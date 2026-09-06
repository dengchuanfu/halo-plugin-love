# Halo 情侣主页插件

`love-page-plugin` 是一个无需数据库的 Halo 插件，用于提供公开的情侣纪念页面。启用后可通过 `/love` 访问。

## 功能

- 提供自适应全屏的情侣主页，桌面端与移动端均可完整展示。
- 页面包含自定义壁纸、情侣头像与昵称、动态爱心、波浪过渡和相恋时长计时器。
- 后台可分别配置两人的名字、QQ 号、头像图片链接、上传头像、头像点击跳转链接、相恋日期、页面标题、纪念文案及自定义壁纸。
- 左右头像来源互不影响，可分别使用 QQ 号、图片链接或上传头像；每一侧的优先级均为：QQ 号、头像图片链接、上传头像。
- 设置头像点击跳转链接后，点击对应头像会在新标签页打开该链接。
- 未上传头像或壁纸时，自动使用插件内置的默认资源。
- 配置保存在 Halo 插件的 ConfigMap 中；不使用独立数据库、SQL 迁移或第三方服务。

## 使用方式

1. 在 Halo 后台的“插件”页面上传并启用插件。
2. 打开插件详情页的“设置”，填写情侣资料并保存。
3. 访问 `https://你的域名/love` 查看页面效果。

## 插件信息

- 作者：Monster
- 仓库：[halo-plugin-love](https://github.com/dengchuanfu/halo-plugin-love)
- 问题反馈：[GitHub Issues](https://github.com/dengchuanfu/halo-plugin-love/issues)

## 本地开发

项目要求使用 JDK 21。进入项目目录后运行：

```bash
./gradlew build
```

构建产物位于 `build/libs/`，可在 Halo 后台上传安装。
