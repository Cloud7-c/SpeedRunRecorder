<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="${refreshInterval}">
    <link rel="stylesheet" href="../templates/css/current.css">
</head>
<body>
<#if enterNether??>
    <div class="achievement-group">
        <img src="../templates/img/enterNether.png" alt="enterNether">
        <span>${enterNether}</span>
    </div>
</#if>
<#if enterBastion??>
    <div class="achievement-group">
        <img src="../templates/img/enterBastion.png" alt="enterBastion">
        <span>${enterBastion}</span>
    </div>
</#if>
<#if enterFortress??>
    <div class="achievement-group">
        <img src="../templates/img/enterFortress.png" alt="enterFortress">
        <span>${enterFortress}</span>
    </div>
</#if>
<#if firstPortal??>
    <div class="achievement-group">
        <img src="../templates/img/firstPortal.png" alt="firstPortal">
        <span>${firstPortal}</span>
    </div>
</#if>
<#if enterStronghold??>
    <div class="achievement-group">
        <img src="../templates/img/enterStronghold.png" alt="enterStronghold">
        <span>${enterStronghold}</span>
    </div>
</#if>
<#if enterEnd??>
    <div class="achievement-group">
        <img src="../templates/img/enterEnd.png" alt="enterEnd">
        <span>${enterEnd}</span>
    </div>
</#if>
<#if finish??>
    <div class="achievement-group">
        <img src="../templates/img/finish.png" alt="finish">
        <span>${finish}</span>
    </div>
</#if>
</body>
</html>
