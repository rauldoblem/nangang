
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `ED_DAILYLOG_TREATMENT`;
CREATE TABLE ED_DAILYLOG_TREATMENT (
  ID VARCHAR (36) NOT NULL COMMENT '主键',
  DLOG_ID VARCHAR (36) COMMENT '值班日志ID',
  DLOG_TREATMENT VARCHAR (500) COMMENT '值班日志办理情况',
  TREAT_BY VARCHAR (20) COMMENT '办理人',
  TREAT_TIME DATETIME COMMENT '办理时间(yyyy-MM-dd HH:mm:SS)',
  TREAT_STATUS CHAR(1) COMMENT '日志办理状态码：0为待办，1为办理中，2为办结',
  PRIMARY KEY (ID)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '值班日志办理';