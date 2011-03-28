[#ftl]
<!DOCTYPE html>
<html>
<head>
    <title>QuikTrik: Ad-hoc todo lists</title>
    <script language="javascript" src="/js/jquery.js"></script>
    <script language="javascript" src="/js/todo.js"></script>
</head>
<body>
<h1>Todo</h1>
<input type="text"/><button id="add">Add</button>
<ul>
[#list todo as entry]
    <li id="l${entry.id}">${entry.start?date}: ${entry.text}<button class="done" id="d${entry.id}">Done</button></li>
[/#list]
</ul>
</body>
</html>