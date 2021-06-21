# 华宇网址导航 (nav-server)

[![site](http://badge.thunisoft.com/badge/site-nav.thunisoft.com-orange.svg?logo=paper-plane)](http://nav.thunisoft.com) [![](http://badge.thunisoft.com/badge/%E8%AE%AE%E9%A2%98-issue-hotpink.svg?logo=bell)](http://open.thunisoft.com/openhub/nav-server/issues) [![Java Version](http://badge.thunisoft.com//badge/Java-1.8-blue.svg)](http://badge.thunisoft.com//badge/Java-1.8-blue.svg) [![SpringBoot](http://badge.thunisoft.com/badge/SpringBoot-2.3.2-.svg)](http://badge.thunisoft.com/badge/SpringBoot-2.3.2-.svg)

> 这是一个展示和管理网址导航信息的服务。http://nav.thunisoft.com

## 一、快速开始

如果你想参与本项目的开发，将本项目 `clone` 到本地之后，然后导入 IDEA 或者 Eclipse 中，启动 SpringBoot 服务即可。

## 二、注意事项

### 1. 复制个性化配置文件

建议你将 `releases/config/application-local.yml` 文件复制到 `src/main/resources` 目录下，该配置文件优先级更高，用于你的自定义配置，该文件会自动被 Git 所忽略，不会被提交，防止提交代码时配置污染。

### 2.导入数据

本服务为了简单，采用了嵌入式数据库，第一次运行，建议你将配置文件中的 `system.load-json-data` 参数设置为 `true`，启动服务时会自动从配置文件中，读取并保存数据，启动完成后，就可以在页面中看到网址导航的相关数据了。

```yaml
# 本系统的相关配置.
system:
  load-json-data: false # 是否从 JSON 配置文件中加载保存网址导航分类和网址信息.
```

### 启动服务

启动服务效果：

![](http://bed.thunisoft.com:9000/ibed/2020/08/04/ARx5Q7nTl.png)
