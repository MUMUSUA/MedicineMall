package com.example.mall.product;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.product.entity.BrandEntity;
import com.example.mall.product.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class MallProductApplicationTests {
    @Autowired
    BrandService brandService;

    @Autowired
    StringRedisTemplate RedisTemplate;

    @Autowired
    RedissonClient redissonClient;
    @Test
    void redisTemplateTest(){
        ValueOperations<String, String> ops = RedisTemplate.opsForValue();

        //保存
        ops.set("hello","world_" + UUID.randomUUID().toString());

        //查询
        String hello = ops.get("hello");
        System.out.println("之前保存的数据:"+hello);
    }
//    @Autowired
//    OSSClient ossClient;
    @Test
    void contextLoads() {
        BrandEntity brandEntity=new BrandEntity();
//        brandEntity.setName("三一药业");
//        brandService.save(brandEntity);
//        System.out.println("保存成功");
//        brandEntity.setBrandId(12L);
//        brandEntity.setDescript("999感冒灵");
//        brandService.updateById(brandEntity);
//        System.out.println("修改成功");
        List<BrandEntity> list= brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id",12L));
        list.forEach((item)->{
            System.out.println(item);
        });
    }

    @Test
    void redission(){
        System.out.println("创建Redission"+redissonClient);
    }

//@Test
//    public void testUpload(){
//
//
////            // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
////            String endpoint = "oss-cn-beijing.aliyuncs.com";
////            // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
////            String accessKeyId = "LTAI5t8Mniv4n3hanc6oonRi";
////            String accessKeySecret = "NemsnmjkrR7e4pMA0uFJHbBCp14SeE";
////            // 填写Bucket名称，例如examplebucket。
////            String bucketName = "medicine--mall";
//            // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。

//            String objectName = "2.jpg";
//            // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
//            // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//            String filePath= "C:\\Users\\Wedimeker\\OneDrive\\文档\\实训项目\\img\\1.jpg";

//            String objectName = "5.jpg";
//            // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
//            // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//            String filePath= "D:\\手机照片\\tute\\tu1.jpg";

//
//            // 创建OSSClient实例。
////            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//            try {
//                InputStream inputStream = new FileInputStream(filePath);
//                // 创建PutObject请求。
//                ossClient.putObject("medicine--mall", objectName, inputStream);
//            } catch (OSSException oe) {
//                System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                        + "but was rejected with an error response for some reason.");
//                System.out.println("Error Message:" + oe.getErrorMessage());
//                System.out.println("Error Code:" + oe.getErrorCode());
//                System.out.println("Request ID:" + oe.getRequestId());
//                System.out.println("Host ID:" + oe.getHostId());
//            } catch (ClientException | FileNotFoundException ce) {
//                System.out.println("Caught an ClientException, which means the client encountered "
//                        + "a serious internal problem while trying to communicate with OSS, "
//                        + "such as not being able to access the network.");
//                System.out.println("Error Message:" + ce.getMessage());
//            } finally {
//                if (ossClient != null) {
//                    ossClient.shutdown();
//                }
//            }
//        }


    }
