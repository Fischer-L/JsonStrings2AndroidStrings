AndroidStringResourcesGenerator
===============================
(Under construction...)


## Usage
We develope one nice android app. This nice android app is open to several countries so preparing the multi-language translations is necessary. We have 10 languages for our app. Under Android framework, one language file is one resource, which is one strings.xml file in one language-specific directory. For our cool app, this rule implies we have to maintain 10 strings.xml files in 10 different directories. If there is one word we want to change, we would have to go to 10 directories and modify 10 xml files. That is really a big annoying work for a tiny change. What's more, it is hard to maintain words in langauges in files. Usually it is better to collect all words in different languages in one source, say, one .json file or one excel file or one database. That would be good that we could maintain one simple source and simply press a magic button then all string.xml files in different directories got updated automatically. The AndroidStringResourcesGenerator project is made for this use case.


## Architecture
The AndroidStringResourcesGenerator project's structure is modularized and defined with interfaces. This characteristic provides the flexibility that adopting it to parse various source formats(like JSON format, excel file or database), while still reusing the main component which generates the strings.xml, is possible. 
