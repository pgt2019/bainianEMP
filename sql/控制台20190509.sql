CREATE TABLE `zh_api_token` (

`id` int(11) NOT NULL COMMENT '主键 只存在一个',

`token` varchar(32) NOT NULL COMMENT 'token',

`create_time` datetime NOT NULL COMMENT '创建时间',

PRIMARY KEY (`id`) 

)

COMMENT = '振汇开放平台  token持久化';



CREATE TABLE `zh_user` (

`id` int(11) NOT NULL,

`
personNumber` varchar(32) NULL COMMENT '人员编号(JSON)',

`
name` varchar(10) NULL COMMENT '姓名',

`
icCard` varchar(32) NULL COMMENT 'ic卡号',

`
idCard` varchar(32) NULL COMMENT 'id卡号',

`
extendInfo` varchar(255) NULL COMMENT '扩展字段',

`sync_time` datetime NOT NULL COMMENT '最后一次同步时间',

PRIMARY KEY (`id`) 

)

COMMENT = '振汇开放平台api  人员信息表';



CREATE TABLE `zh_equipment` (

`id` int(11) NOT NULL,

`meid` varchar(32) NULL COMMENT '设备号',

`key` varchar(64) NULL COMMENT '激活码',

`create_by` varchar(255) NULL COMMENT '创建人',

`create_time` datetime NOT NULL COMMENT '创建时间',

PRIMARY KEY (`id`) 

)

COMMENT = '振汇开放平台 设备接口绑定';



