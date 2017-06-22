CREATE TABLE `tbl_catalog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `catalog_no` varchar(64) NOT NULL DEFAULT '' COMMENT '商品类别编号',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '类别名称',
  `icon` varchar(128) NOT NULL DEFAULT '' COMMENT '类别图标url',
  `pid` int(11) DEFAULT NULL COMMENT '父id',
  `description` varchar(256) NOT NULL DEFAULT '' COMMENT '类别描述',
  `is_delete` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0:否, 1:是',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_catalog_no` (`catalog_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '商品类别表';

CREATE TABLE `tbl_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_no` varchar(64) NOT NULL DEFAULT '' COMMENT '商品编号',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '商品名称',
  `catalog_no` varchar(64) NOT NULL DEFAULT '' COMMENT '商品类别',
  `description` varchar(256) NOT NULL DEFAULT '' COMMENT '商品规格简介,',
  `price` decimal(10,2) NOT NULL DEFAULT 0 COMMENT '商品定价',
  `now_price` decimal(10,2) NOT NULL DEFAULT 0 COMMENT '现价',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态 0:新增, 1:已上架, 2:已下架, 3:已删除',
  `icon` varchar(128) NOT NULL DEFAULT '' COMMENT '商品图标',
  `detail` varchar(1024) NOT NULL DEFAULT '' COMMENT '商品详情',
  `images` varchar(1024) NOT NULL DEFAULT '' COMMENT '商品图片集合,逗号分隔',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_product_no` (`product_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '商品信息表';

CREATE TABLE `tbl_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(64) NOT NULL DEFAULT '' COMMENT '用户uid',
  `rec_name` varchar(64) NOT NULL DEFAULT '' COMMENT '收货人姓名',
  `province` varchar(64) NOT NULL DEFAULT '' COMMENT '省份',
  `city` varchar(64) NOT NULL DEFAULT '' COMMENT '城市',
  `area` varchar(64) NOT NULL DEFAULT '' COMMENT '区域',
  `pca_detail` varchar(128) NOT NULL DEFAULT '' COMMENT '详细地址',
  `address_detail` varchar(128) NOT NULL DEFAULT '' COMMENT '详细地址',
  `postcode` varchar(64) NOT NULL DEFAULT '' COMMENT '邮编',
  `phone` varchar(64) NOT NULL DEFAULT '' COMMENT '联系电话',
  `is_default` int(1) NOT NULL DEFAULT 0 COMMENT '是否默认地址 0:否, 1:是',
  `is_delete` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0:否, 1:是',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户地址表';

CREATE TABLE `tbl_order_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '订单编号',
  `uid` varchar(64) NOT NULL DEFAULT '' COMMENT '用户uid',
  `product_no` varchar(64) NOT NULL DEFAULT '' COMMENT '商品编号',
  `total_quantity` int(11) NOT NULL DEFAULT 0 COMMENT '商品总数量',
  `total_amount` decimal(10,2) NOT NULL DEFAULT 0 COMMENT '订单总金额',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态 0:初始化, 1:支付成功, 2:配送中, 3:订单完成, 4:订单取消',
  `delivery_type` varchar(64) NOT NULL DEFAULT '' COMMENT '配送类型 daily:每天 weekly:每周 monthly:每月',
  `delivery_time` varchar(64) NOT NULL DEFAULT '' COMMENT '配送时间点,与配送类型结合 如:1表示每天1点或每周一或每月1号...',
  `sub_quantity` int(11) NOT NULL DEFAULT 0 COMMENT '配送子订单商品数量',
  `total_sub` int(11) NOT NULL DEFAULT 0 COMMENT '订单总配送次数',
  `finish_sub` int(11) NOT NULL DEFAULT 0 COMMENT '已完成配送次数',
  `next_sub` varchar(64) NOT NULL DEFAULT '' COMMENT '下次配送子订单号',
  `next_sub_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '下次配送时间',
  `address_id` int(11) NOT NULL DEFAULT 0 COMMENT '收货地址',
  `user_number` int(11) NOT NULL DEFAULT 0 COMMENT '使用人数',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '订单信息表';

CREATE TABLE `tbl_sub_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sub_order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '配送编号',
  `order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '订单编号',
  `product_no` varchar(64) NOT NULL DEFAULT '' COMMENT '订单编号',
  `quantity` int(11) NOT NULL DEFAULT 0 COMMENT '商品配送数量',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态 0:待发货, 1:已发货, 2:已签收(配送完成)',
  `recv_name` varchar(64) NOT NULL DEFAULT '' COMMENT '收货地址',
  `phone` varchar(64) NOT NULL DEFAULT '' COMMENT '收货地址',
  `address` varchar(256) NOT NULL DEFAULT '' COMMENT '收货地址',
  `delivery_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '默认配送时间',
  `used_percent` int(11) NOT NULL DEFAULT 0 COMMENT '使用百分比',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_sub_order_no` (`sub_order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '订单单次配送信息表';


CREATE TABLE `tbl_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(64) NOT NULL DEFAULT '' COMMENT '用户uid',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '用户名称',
  `phone` varchar(64) NOT NULL DEFAULT '' COMMENT '注册手机号',
  `password` varchar(128) NOT NULL DEFAULT '' COMMENT '密码',
  `gender` enum('m','f','s') NOT NULL DEFAULT 's' COMMENT '性别 m:男, f:女, s:保密',
  `birthday` date NOT NULL DEFAULT '0000-00-00' COMMENT '生日',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态 0:可用, 1:冻结',
  `last_login_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '上次登录时间',
  `last_login_ip` varchar(128) NOT NULL DEFAULT '' COMMENT '上次登录ip',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_uid` (`uid`),
  UNIQUE KEY `uni_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户信息表';

CREATE TABLE `tbl_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `feedback_no` varchar(64) NOT NULL DEFAULT '' COMMENT '编号',
  `uid` varchar(64) NOT NULL DEFAULT '' COMMENT '用户',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '反馈内容',
  `images` varchar(1024) NOT NULL DEFAULT '' COMMENT '上传图片集合,逗号分隔',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_feedback_no` (`feedback_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户反馈信息表';

CREATE TABLE `tbl_message_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(64) NOT NULL DEFAULT '' COMMENT '消息流水',
  `channel_message_id` varchar(64) DEFAULT NULL COMMENT '消息通道流水',
  `message_type` int(1) NOT NULL DEFAULT 0 COMMENT '消息类型 0:含验证码的短信, 1:无验证码通知短信',
  `message_channel` varchar(64) NOT NULL DEFAULT '' COMMENT '消息发送系统通道名称',
  `template_code` varchar(64) NOT NULL DEFAULT '' COMMENT '模版编号',
  `message_address` varchar(64) NOT NULL DEFAULT '' COMMENT '消息发送地址',
  `send_status` int(1) NOT NULL DEFAULT 0 COMMENT '消息发送状态 0:初始化 1:处理中 2:发送成功 3:发送失败',
  `result_code` varchar(64) NOT NULL DEFAULT '' COMMENT '消息发送结果码',
  `result_info` varchar(64) NOT NULL DEFAULT '' COMMENT '消息发送结果描述',
  `send_content` varchar(1024) NOT NULL DEFAULT '' COMMENT '消息内容',
  `vcode` varchar(64) NOT NULL DEFAULT '' COMMENT '验证码',
  `validate_status` int(1) NOT NULL DEFAULT 0 COMMENT '验证码校验状态 0:未校验 1:已校验',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `send_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '消息发送时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_message_id` (`message_id`),
  UNIQUE KEY `uni_channel_message_id` (`message_channel`,`channel_message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '消息表';

