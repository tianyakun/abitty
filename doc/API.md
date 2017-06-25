# 接口文档
Base Url：www.abitty.com
IP：47.93.17.130
## 视图页
地址: /view/*
## 登录页
地址: /loginIndex

## 获取商品类别列表

## 1.获取商品类别列表
地址:	/catalog/list    

方式:	get/post    

请求参数:     
```
```
返回参数:
```
{
    "retCode": "000000",
    "retMsg": "SUCCESS",
    "data": {
        "list": [
            {
                "catalogNo": "CN1497713023528",
                "name": "酸奶",
                "icon": "",
                "description": ""
            },
            {
                "catalogNo": "CN1497713024644",
                "name": "抽纸",
                "icon": "",
                "description": ""
            }
        ]
    }
}
```
## 2.获取单个类别
地址:	/catalog/detail/{catalogNo}    

方式:	get/post    

请求参数：
```
catalogNo=CN1497713023528
```
返回参数：
```
{
    "retCode": "000000",
    "retMsg": "SUCCESS",
    "data": {
        "item": {
            "catalogNo": "CN1497713023528",
            "name": "酸奶",
            "icon": "",
            "description": ""
        }
    }
}
```
## 3.获取商品列表
地址:	/product/list/{catalogNo}     

方式:	get/post    

请求参数：
```
catalogNo=CN1497713023528
```
返回参数：
```
{
    "retCode": "000000",
    "retMsg": "SUCCESS",
    "data": {
        "list": [
            {
                "productNo": "PN1497714192404",
                "name": "酸奶1",
                "catalogNo": "CN1497713023528",
                "description": "酸奶1酸奶1",
                "price": 99,
                "nowPrice": 88,
                "icon": "",
                "detail": "酸奶1酸奶1酸奶1酸奶1",
                "images": ""
            },
            {
                "productNo": "PN1497714193460",
                "name": "酸奶2",
                "catalogNo": "CN1497713023528",
                "description": "酸奶2酸奶2",
                "price": 90,
                "nowPrice": 85,
                "icon": "",
                "detail": "酸奶2酸奶2酸奶2酸奶2",
                "images": ""
            }
        ]
    }
}
```
## 4.获取单个商品详情
地址:	/product/detail/{productNo}    

方式:	get/post    

请求参数：
```
productNo=PN1497714192404
```
返回参数：
```
{
    "retCode": "000000",
    "retMsg": "SUCCESS",
    "data": {
        "item": {
            "productNo": "PN1497714192404",
            "name": "酸奶1",
            "catalogNo": "CN1497713023528",
            "description": "400ml*5瓶", //商品规格
            "price": 99,
            "nowPrice": 88,
            "icon": "",
            "detail": "酸奶1酸奶1酸奶1酸奶1",
            "images": "",
            "deliveryType": "weekly" //配送周期 weekly：按周配送，monthly：按月配送，yearly：按年配送
        }
    }
}
```
## 5.创建订单并调用微信统一下单接口预支付
地址:	/order/confirm    

方式:	post    

请求参数：
```
productNo=PN1497714192404
totalQuantity=4 //商品总数
totalAmount=352 //订单总金额
serviceAtomCount=1 //服务次数（即订购月数）
deliveryType=weekly //配送周期 weekly、monthly、yearly
subQuantity=1 //单次配送数量
totalSub=4 //配送次数
userNumber=2 //使用人数
remark=remark //备注
receiverName=麦克斯韦 //以下为收货人信息
phoneNumber=18812345678
addressProvince=北京
addressCity=北京市
addressArea=昌平区
addressDetail=龙域中路融泽嘉园1号院
postcode=102200
productBody=一点生活-酸奶 //商品描述 商家名称-销售商品类目
openidCode=xxxxxxx //微信支付时获取用户openid的code
```
返回参数：
```
{
    "retCode": "000000",
    "retMsg": "SUCCESS",
    "data": {
        timeStamp: "234234234",//时间戳
			nonceStr: "xxxxx",//16位随机字符串
			package: "sdxxxxx",//商品签名
    		signType: "MD5", //签名算法
    		paySign: "xxxx" //支付签名
    }
}
```
## 6.JS-SDK权限获取
地址:	/wechat/ticket/{code}    
方式:	get/post    
请求参数：
```
code=xxxxx
```
返回参数：
```
{
    "retCode": "000000",
    "retMsg": "SUCCESS",
    "data": {
        jsapi_ticket: 'xxxx',//公众号JS-SDK使用权限签名
    		noncestr: 'xxx',//16位随机字符串
    		timestamp: 'xxx',//时间戳
    		signature: 'xxxx',//JS各接口签名
    		appid: 'xxxxxx'//公众号id
    }
}
```
## 7.获取订单列表
地址:	/order/list    

方式:	post    

请求参数：
```
```
返回参数：
```
{
    "retCode": "000000",
    "retMsg": "000000",
    "data": {
        "list": [
            {
                "orderNo": "order325801103440478208",
                "uid": "18812345678",
                "productNo": "PN1497714192404",
                "productName": "酸奶1",
                "productIcon": "",
                "totalQuantity": 4,
                "totalAmount": 352,
                "status": 1,
                "deliveryType": "weekly",
                "subQuantity": 1,
                "totalSub": 4,
                "finishSub": 0,
                "nextSub": "sub325804314515734528",
                "nextSubTime": "2017-06-21 00:57:16",
                "intervalDays": 3,
                "progress": "42%",
                "userNumber": 2,
                "remark": "remark"
            }
        ]
    }
}
```
## 8.获取订单详情
地址:	/order/detail/{orderNo}    

方式:	post    

请求参数：
```
orderNo=order325801103440478208
```
返回参数：
```
{
    "retCode": "000000",
    "retMsg": "000000",
    "data": {
        "detail": {
            "orderInfo": {
                "orderNo": "order325801103440478208",
                "uid": "18812345678",
                "productNo": "PN1497714192404",
                "productName": "酸奶1",
                "productIcon": "",
                "totalQuantity": 4,
                "totalAmount": 352,
                "status": 1,
                "deliveryType": "weekly",
                "subQuantity": 1,
                "totalSub": 4,
                "finishSub": 0,
                "nextSub": "sub325804314515734528",
                "nextSubTime": "2017-06-21 00:57:16",
                "intervalDays": 3,
                "progress": "42%",
                "userNumber": 2,
                "remark": "remark"
            },
            "subList": [
                {
                    "subOrderNo": "sub325804314515734528",
                    "orderNo": "order325801103440478208",
                    "productNo": "PN1497714192404",
                    "quantity": 1,
                    "status": 0,
                    "recvName": "麦克斯韦",
                    "phone": "18812345678",
                    "address": "北京北京市昌平区xxxxxxxxxxx",
                    "deliveryTime": "2017-06-21 00:57:16",
                    "usedPercent": 0,
                    "remark": ""
                },
                {
                    "subOrderNo": "sub325804314519928832",
                    "orderNo": "order325801103440478208",
                    "productNo": "PN1497714192404",
                    "quantity": 1,
                    "status": 0,
                    "recvName": "麦克斯韦",
                    "phone": "18812345678",
                    "address": "xxxxxxxxxxx",
                    "deliveryTime": "2017-06-28 00:57:16",
                    "usedPercent": 0,
                    "remark": ""
                },
                {
                    "subOrderNo": "sub325804314519928833",
                    "orderNo": "order325801103440478208",
                    "productNo": "PN1497714192404",
                    "quantity": 1,
                    "status": 0,
                    "recvName": "麦克斯韦",
                    "phone": "18812345678",
                    "address": "xxxxxxxxxxx",
                    "deliveryTime": "2017-07-05 00:57:16",
                    "usedPercent": 0,
                    "remark": ""
                },
                {
                    "subOrderNo": "sub325804314519928834",
                    "orderNo": "order325801103440478208",
                    "productNo": "PN1497714192404",
                    "quantity": 1,
                    "status": 0,
                    "recvName": "麦克斯韦",
                    "phone": "18812345678",
                    "address": "xxxxxxxxxxx",
                    "deliveryTime": "2017-07-12 00:57:16",
                    "usedPercent": 0,
                    "remark": ""
                }
            ]
        }
    }
}
```
## 9.发送短信验证码
地址:	/verify/send    

方式:	get/post    

请求参数：
```
phone=18812345678
```
返回参数：
```
{
    "retCode": "000000",
    "retMsg": "SUCCESS",
    "data": {
        "messageId": "M325821367628857344"
    }
}
```

## 10.用户登录／注册
地址:	/login    

方式:	post    

请求参数：
```
phone=18812345678
messageId=M325821367628857344
verifyCode=123456
```
返回参数：
```
{
    "retCode": "000000",
    "retMsg": "SUCCESS",
    "data": {}
}
```
## 11.查看用户信息
地址:	/my/account    

方式:	get/post    

请求参数：
```
```
返回参数：
```
{
    "retCode": "000000",
    "retMsg": "SUCCESS",
    "data": {
        "item": {
            "uid": "18812345678",
            "gender": "s",
            "birthday": "2017-06-18"
        }
    }
}
```
## 12.更新用户信息
地址:	/my/account/update    

方式:	post    

请求参数：
```
gender=m
birthday=1999-10-01
```
返回参数：
```
{
    "retCode": "000000",
    "retMsg": "SUCCESS",
    "data": {}
}
```




