## VM OPTIONS

jdk当前版本17 dubbo调用时在命令行终端抛异常 根因是jdk升级 在启动命令中增加如下

```shell
--add-opens java.base/java.math=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED 
```