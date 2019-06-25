package com.taiji.emp.nangang.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.emp.nangang.entity.Weather;
import com.taiji.emp.nangang.mapper.WeatherMapper;
import com.taiji.emp.nangang.service.WeatherService;
import com.taiji.emp.nangang.vo.TotalWeatherDataVo;
import com.taiji.emp.nangang.vo.TotalWeatherVo;
import com.taiji.emp.nangang.vo.WeatherVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/11/20 9:08
 */
@Slf4j
//@EnableScheduling
@AllArgsConstructor
@Component
public class WeatherTask {

    @Autowired
    private WeatherService service;
    @Autowired
    private WeatherMapper mapper;

    /**
     * 访问接口地址，获得天气字符串，封装解析成List<Weather></>
     * @return List<Weather>
     */
    private List<Weather> getWeatherInfo() {
        String strUrl = "http://t.weather.sojson.com/api/weather/city/101031100";
        String result = "";
        URLConnection uc;
        InputStreamReader inputStreamReader;
        BufferedReader in;
        TotalWeatherVo totalWeatherVo;
        ArrayList<WeatherVo> list = null;
        try {
            URL url = new URL(strUrl);
            uc = url.openConnection();
            inputStreamReader = new InputStreamReader(uc.getInputStream() , "UTF-8");
            in = new BufferedReader(inputStreamReader);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            ObjectMapper mapper = new ObjectMapper();
            totalWeatherVo = mapper.readValue(result, TotalWeatherVo.class);
            list = new ArrayList<>();
            //把总的data的yesterday和forecast的温度的字符改了
            TotalWeatherDataVo dataVo = totalWeatherVo.getData();
            String humidity = dataVo.getHumidity();
            String temperature = dataVo.getTemperature();

            //昨天天气数据
            WeatherVo yesVo = dataVo.getYesterday();
            //将 高温 9.0℃ 处理成 9.0℃
            yesVo.setHigh(yesVo.getHigh().substring(3));
            yesVo.setLow(yesVo.getLow().substring(3));
            list.add(yesVo);

            List<WeatherVo> forecastList = dataVo.getForecast();
            if(!CollectionUtils.isEmpty(forecastList)){
                //今天数据
                WeatherVo todayVo = forecastList.get(0);
                todayVo.setHumidity(humidity);
                todayVo.setTemperature(temperature);
                todayVo.setHigh(todayVo.getHigh().substring(3));
                todayVo.setLow(todayVo.getLow().substring(3));
                list.add(todayVo);

                //预报数据
                forecastList.remove(todayVo);
                //list.addAll(forecastList);
                for (WeatherVo weatherVo : forecastList) {
                    weatherVo.setHigh(weatherVo.getHigh().substring(3));
                    weatherVo.setLow(weatherVo.getLow().substring(3));
                    list.add(weatherVo);
                }
            }
            in.close();
            inputStreamReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapper.voListToEntityList(list);
    }

    /**
     * SpringTask定时任务
     * 每天0点开始，隔四个小时刷新一次
     */
    @Scheduled(cron = "0 0 0,4,8,12,16,20 * * ?")
    public void weatherJob(){

        /**
         * 在这个位置 加上判断数据库中表数据的创建时间
         *
         * 解决分布式 定时任务重复执行问题
         */
        //检查 job是否已经在其他服务器执行，已执行返回true
        //boolean checkResult = service.checkJob();
        /**
         * **************************************
         */
        //if(!checkResult){
            List<Weather> weathers = getWeatherInfo();
            boolean empty = CollectionUtils.isEmpty(weathers);
            Assert.isTrue(!empty , "获取天气情况异常");
            List<Weather> result = service.saveAll(weathers);
            boolean isEmpty = CollectionUtils.isEmpty(result);
            Assert.isTrue(!isEmpty , "天气情况保存失败");
            if(!isEmpty){
                System.out.println("获取天气成功");
            }
       // }
    }
}
