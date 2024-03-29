--   --------------------------------------------------
--   Generated by Enterprise Architect Version 11.0.1106
--   Created On : 星期二, 18 九月, 2018
--   DBMS       : Oracle
--   --------------------------------------------------





--  Create Tables
CREATE TABLE DOC_ATTACHMENT
(
	GUID              VARCHAR2(36) NOT NULL,    --  主键
	YWID              VARCHAR2(36),    --  各类业务主键ID
	NAME              VARCHAR2(100),    --  文件名
	TYPE              VARCHAR2(20),    --  文件类型
	SUFFIX            VARCHAR2(10),    --  文件后缀名
	LOCATION          VARCHAR2(200),    --  存储地址
	UPLOAD_TIME       Date,    --  上传时间
	UPLOAD_USER_ID    VARCHAR2(36),    --  上传用户编码
	UPLOAD_USER_NAME  VARCHAR2(20),    --  上传用户姓名
	DEL_FLAG          CHAR(1)    --  删除标记[0：已删除；1：未删除]
)
;

COMMENT ON TABLE DOC_ATTACHMENT IS '附件表'
;
COMMENT ON COLUMN DOC_ATTACHMENT.GUID              IS '主键'
;
COMMENT ON COLUMN DOC_ATTACHMENT.YWID              IS '各类业务主键ID'
;
COMMENT ON COLUMN DOC_ATTACHMENT.NAME              IS '文件名'
;
COMMENT ON COLUMN DOC_ATTACHMENT.TYPE              IS '文件类型'
;
COMMENT ON COLUMN DOC_ATTACHMENT.SUFFIX            IS '文件后缀名'
;
COMMENT ON COLUMN DOC_ATTACHMENT.LOCATION          IS '存储地址'
;
COMMENT ON COLUMN DOC_ATTACHMENT.UPLOAD_TIME       IS '上传时间'
;
COMMENT ON COLUMN DOC_ATTACHMENT.UPLOAD_USER_ID    IS '上传用户编码'
;
COMMENT ON COLUMN DOC_ATTACHMENT.UPLOAD_USER_NAME  IS '上传用户姓名'
;
COMMENT ON COLUMN DOC_ATTACHMENT.DEL_FLAG          IS '删除标记[0：已删除；1：未删除]'
;


--  Create Primary Key Constraints
ALTER TABLE DOC_ATTACHMENT ADD CONSTRAINT PK_doc_attachment
	PRIMARY KEY (GUID)
 USING INDEX
;
