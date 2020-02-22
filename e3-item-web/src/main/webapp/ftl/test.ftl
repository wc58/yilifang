<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${titel}</title>
</head>
<body >
    <h1>ShuXing</h1>
    ${id}<br/>
    ${name}<br/>
    ${age}
    <hr>
    <h1>pojo</h1>
    ${pojo.id}<br/>
    ${pojo.name}<br/>
    ${pojo.age}
    <h1>list</h1>
    <table>
    <#list list as pojo>
    <#if pojo_index%2 == 0>
    <tr bgcolor="red">
    <#else>
    <tr bgcolor="blue">
    </#if>
    <td>${pojo.id}</td>
    <td>${pojo.name}</td>
    <td>${pojo.age}</td></tr>
    </#list>
    </table>
    <h1>ifnull</h1>
    <#if falg?? >
    !null
    <#else>
    null
    </#if>
    <hr>
</body>
</html>