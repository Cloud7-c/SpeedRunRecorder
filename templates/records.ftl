<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="${refreshInterval}">
    <link rel="stylesheet" href="../templates/css/records.css">
</head>
<body>
<table>
    <tr>
        <th>Date</th>
        <th><img src="../templates/img/enterNether.png" alt="enteredNether"></th>
        <th><img src="../templates/img/enterBastion.png" alt="enteredBastion"></th>
        <th><img src="../templates/img/enterFortress.png" alt="enteredFortress"></th>
        <th><img src="../templates/img/firstPortal.png" alt="firstPortal"></th>
        <th><img src="../templates/img/enterStronghold.png" alt="enteredStronghold"></th>
        <th><img src="../templates/img/enterEnd.png" alt="enteredEnd"></th>
        <th><img src="../templates/img/finish.png" alt="finished"></th>
    </tr>
    <#if renderBestRecord>
        <tr>
            <td>Best</td>
            <td>${(bestRecord.enterNether)!""}</td>
            <td>${(bestRecord.enterBastion)!""}</td>
            <td>${(bestRecord.enterFortress)!""}</td>
            <td>${(bestRecord.firstPortal)!""}</td>
            <td>${(bestRecord.enterStronghold)!""}</td>
            <td>${(bestRecord.enterEnd)!""}</td>
            <td>${(bestRecord.finish)!""}</td>
        </tr>
    </#if>
    <#if renderAverageRecord>
        <tr>
            <td>Average</td>
            <td>${(averageRecord.enterNether)!""}</td>
            <td>${(averageRecord.enterBastion)!""}</td>
            <td>${(averageRecord.enterFortress)!""}</td>
            <td>${(averageRecord.firstPortal)!""}</td>
            <td>${(averageRecord.enterStronghold)!""}</td>
            <td>${(averageRecord.enterEnd)!""}</td>
            <td>${(averageRecord.finish)!""}</td>
        </tr>
    </#if>
    <#list records?reverse as record>
        <tr>
            <td>${record.start}</td>
            <td>${(record.enterNether)!""}</td>
            <td>${(record.enterBastion)!""}</td>
            <td>${(record.enterFortress)!""}</td>
            <td>${(record.firstPortal)!""}</td>
            <td>${(record.enterStronghold)!""}</td>
            <td>${(record.enterEnd)!""}</td>
            <td>${(record.finish)!""}</td>
        </tr>
    </#list>
</table>
</body>
</html>
