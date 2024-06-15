[English](./README.md) | 简体中文

# SpeedRunRecorder

这是一个用于 Minecraft RSG 速通的本地记录与 OBS 覆盖生成工具。

在线记录与 OBS 覆盖工具请见：https://paceman.gg

## 功能

- 自动记录每场速通中关键进度达成的时间（进入下界、进入猪灵堡垒、进入下界要塞、盲传、进入要塞、进入末地、完成)；
- 生成关键进度达成的数量、最好时间、平均时间；
- 生成 HTML 文件，用于 OBS 覆盖。

## 要求

- SpeedRunIGT 14.1+
- 使用 Atum 自动重开并设置为 RSG 模式
- Java 12+

## 使用方法

### OBS 覆盖

启动工具后在 overlay 文件夹下会自动生成 HTML 文件，在 OBS 中选择添加源-浏览器，将这些 HTML 文件作为本地文件添加到 OBS 中即可。默认样式支持自适应换行，请在 OBS 中调整浏览器源大小以实现横向/纵向排列。

- `count.html` -> 速通中达成的关键进度计数
- `average.html` -> 速通中达成关键进度的平均时间
- `current.html` -> 当前速通中所达成的关键进度及时间
- `records.html` -> 每场速通的开始时间、达成关键进度的时间以及最好时间和平均时间

### 偏好设置

可在`config/preferences.json`文件中进行一些自定义设置，下一次启动工具时生效。默认设置如下。

```json
{
  "generateAverage": true,
  "generateCurrent": true,
  "generateCount": true,
  "generateRecords": true,
  "loadHistoryRecords": true,
  "renderBestRecord": true,
  "renderAverageRecord": true,
  "refreshInterval": 5
}
```

- `generateAverage` -> 是否生成`average.html`
- `generateCurrent` -> 是否生成`current.html`
- `generateCount` -> 是否生成`count.html`
- `generateRecords` -> 是否生成`records.html`
- `loadHistoryRecords` -> 是否加载历史速通记录
- `renderBestRecord` -> 是否在`records.html`中生成最好时间记录
- `renderAverageRecord` -> 是否在`records.html`中生成平均时间记录
- `refreshInterval` -> HTML 自动刷新时间（秒）

### 自定义

templates 文件夹下存放了 HTML 模板文件（*.ftl）、css 样式文件以及图标，可自定义生成的 HTML 文件样式。

了解更多 ftl 语法：https://freemarker.apache.org/

## 注意事项

- 若双击 jar 无法启动，请使用 start.bat。
- Fortress First 将不会计入到关键进度计数、平均时间、最好时间中。
- `current.html`在当场速通结束后不会立即清空，在下场速通达成某些事件时（如获得铁锭）才会刷新。

## 已知问题

启动工具时，最后一场速通有时会错误地重复加载2次，可通过重启工具解决。
