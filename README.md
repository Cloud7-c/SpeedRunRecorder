English | [简体中文](./README_zh.md)

# SpeedRunRecorder

A tool for recording key milestones in Minecraft RSG speedruns locally and generating OBS overlays.

If you need an online version, see: https://paceman.gg

## Features

- Automatically record the time of key milestones in each speedrun (Entering the Nether, Entering Bastion, Entering Nether Fortress, Blinding, Entering Stronghold, Entering End, Finish);
- Generate the count, best time, and average time of key milestones achieved;
- Generate HTML files for OBS overlays.

## Requirements

- SpeedRunIGT 14.1+
- Using Atum for automatic restart and setting it to RSG mode
- Java 12+

## Usage

### OBS Overlay

After starting the tool, HTML files will be automatically generated in the overlay folder. In OBS, select “Add Source” -> “Browser” and add these HTML files as local files to OBS. The default style supports adaptive line breaks. Please adjust the size of the browser source in OBS to achieve horizontal/vertical arrangement.

- `count.html` -> Count of key milestones achieved in speedruns
- `average.html` -> Average time of key milestones achieved in speedruns
- `current.html` -> Key milestones achieved and their times in the current speedrun
- `records.html` -> The start time and the time of key milestones achieved of each speedrun, the best and average time of all speedruns.

### Preferences

Custom settings can be made in the `config/preferences.json` file and will take effect upon restarting the tool. The default settings are as follows.

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

- `generateAverage` -> Whether to generate `average.html`
- `generateCurrent` -> Whether to generate `current.html`
- `generateCount` -> Whether to generate `count.html`
- `generateRecords` -> Whether to generate `records.html`
- `loadHistoryRecords` -> Whether to load historical speedrun records
- `renderBestRecord` -> Whether to generate the best time record in `records.html`
- `renderAverageRecord` -> Whether to generate the average time record in `records.html`
- `refreshInterval` -> HTML auto-refresh interval (seconds)

### Customization

The `templates` folder contains HTML template files (*.ftl), CSS style files, and icons for customizing the generated HTML files.

Learn more about ftl syntax: https://freemarker.apache.org/

## Notes

- If the jar file cannot be launched by double-clicking, please use start.bat.
- Fortress First will not be counted towards the key milestones count, average time, and best time.
- `current.html` will not be cleared immediately after the current speedrun ends. It will refresh when certain events are achieved in the next speedrun (e.g., obtaining iron ingots).

## Known Issues

The last speedrun may be loaded twice incorrectly when starting the tool. This can be resolved by restarting the tool.