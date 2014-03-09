JsonStrings2AndroidStrings
===============================


## Usage
This project parses string resources in .json files and then pass the parsed resources to the AndroidStringResourcesGenerator. Utilize the AndroidStringResourcesGenerator project to generate strings.xml files.


## String resources in JSON

Suppoes we support two languages, en(English) and es(Espanol), and en is the default language so three strings.xml files should be genrated as below:

### values/strings.xml
```xml
<string name="title">
	Title
</string>
<string-array name="country_list">
	<item>Canada</item>
	<item>Norway</item>
</string-array>project
<plurals name="number_of_songs">
	<item quantity="one">One song</item>
	<item quantity="other">%d song</item>
</plurals>
```

### values-en/strings.xml
```xml
<string name="title">
	Title
</string>
<string-array name="country_list">
	<item>Canada</item>
	<item>Norway</item>
</string-array>
<plurals name="number_of_songs">
	<item quantity="one">One song</item>
	<item quantity="other">%d song</item>
</plurals>
```

### values-es/strings.xml
```xml
<string name="title">
	Title_in_Espanol
</string>
<string-array name="country_list">
	<item>Canada_in_Espanol</item>
	<item>Norway_in_Espanol</item>
</string-array>
<plurals name="number_of_songs">
	<item quantity="one">One song_in_Espanol</item>
	<item quantity="other">%d song_in_Espanol</item>
</plurals>
```

If we maintain 3 strings.xml files, one day we wnat to change the plurals node's name attribute form "number_of_songs" to "number_of_songs_available", we must modify these 3 files one by one. That would be better to put all the string resource in one .json source file for tracking and maintaining.


### JSON source format

For the above case, follow the below JSON format to store the string resources in the .json file and throw this .json source to the Js2As program.
The Js2As program will generate those 3 strings.xml files for you automatically. When we want to change the name attribute of one plurals node, it only requires modifying one line in this .json source file.

```javascript
{

	// The default lang for all string resources, shall be one of the supported langs in the below string resources.
	"default_lang" : "en",
	
	// The array of the string nodes in the strings.xml. Each object in the array represents one string node.
	// The name of this array, "string", is exactly equal to the string node's tag name, <string>
	"string" : [	
		
		// This object stores one string node's resources in different languages.
		{
			// The value of the name attribute
			"name" : "title",
			
			// The array storing the string values in the supported langs
			"resources" : [ 
				
				// This object represents the string node's value
				{
					// The field indicating which language this string value belongs to, for example this field says the supported lang is English(en).
					"lang" : "en",
					
					// The string value, for example, this field is the word, "Title", in Engish
					"strValue" : "Title" 
				},
				{
					"lang" : "es",
					"strValue" : "Title_in_Espanol"
				}
			]
		},
		...
	],
	
	
	// The array of the string-array nodes in the strings.xml. Each object in the array represents one string-array node.
	// The name of this array, "string-array", is exactly equal to the string-array node's tag name, <string-array>.
	"string-array" : [
	
		// This object storing one string-array node reources in different langauges
		{
			// The value of the name attribute
			"name" : "country_list",
			
			// The array storing the string-array node's item resources in different supported languages.
			"resources" : [
				
				// The object storing the string-array reource for one specific langauge
				{
					// The field indicating which language this string value belongs to. For example this field says the supported lang is English(en).
					"lang" : "en",
					
					// The array storing the items nodes' strings in one sepecific language. For example, this array stores the country list in English
					"items" : ["Canada", "Norway"]
				},				
				{
					"lang" : "es",
					"items" : ["Canada_in_Espanol", "Norway_in_Espanol"]
				}
			]
		
		},
		...
	],
	
	
	// The array of the plurals(quantity strings) nodes in the strings.xml. Each object in the array represents one plurals node.
	// The name of this array, "plurals", is exactly equal to the plurals node's tag name, <plurals>.
	"plurals" : [
		// This object storing the info/values of one plurals node reource
		{
			// The value of the name attribute
			"name" : "number_of_songs",
			
			// The array storing the plural node's items. Each array element stores one item node's data
			"resources" : [
			
				// The object storing one item node's data
				{				
					// The value of the item node's quantity attribute
					"quantity" : "one",
					
					// The array storing all the string values for this quantity in the supported langs
					"strings" : [
						{
							// The field indicating which language this string value belongs to, for example this field says the supported lang is English(en).
							"lang" : "en",
							
							// The string value, for example, this field is the word, "One song", in Engish
							"value" : "One song"
							
						},
						{
							"lang" : "es",
							"value" : "One song_in_Espanol"
						}
					]
				},
			
				// This 2nd object represents the 2nd item node in this plurals(with name="number_of_songs") node.
				{				
					"quantity" : "other",
					
					"strings" : [
						{
							"lang" : "en",
							"value" : "%d song" 							
						},
						{
							"lang" : "es",
							"value" : "%d song_in_Espanol"
						}
					]
				}
			]
		}
	]
}
```

