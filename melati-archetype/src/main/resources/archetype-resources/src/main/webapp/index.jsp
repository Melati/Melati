#macro ( capitalise $stringIn )
  #set ($firstChar = $stringIn.substring(0,1).toUpperCase())
  #set ($rest = $stringIn.substring(1))
${firstChar}${rest}#end
<html>
<head>
<title>
#capitalise(${artifactId}) Home 
</title>
</head>
<body>
<h2>Administer #capitalise(${artifactId}) Database </h2>
<h3><a href="Admin/${artifactId}/Main">Admin</a></h3>
</body>
</html>
