# CSV Testing

[![license](https://img.shields.io/github/license/interlok-testing/testing_csv.svg)](https://github.com/interlok-testing/testing_csv/blob/develop/LICENSE)
[![Actions Status](https://github.com/interlok-testing/testing_csv/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/interlok-testing/testing_csv/actions/workflows/gradle-build.yml)

Project tests interlok-csv features

## What it does

This project contains various workflows that make up a CSV transform API & a CSV to db API.

Each workflow is made up of:
* a jetty-message-consumer that consumes parameters from a given URI path
* a service from the interlok-csv or interlok-csv-json component that will do the transform / database operations
* a jetty-standard-response-producer that returns the payload result back to the requester


## Getting started

* `./gradlew clean build`
* `(cd ./build/distribution && java -jar lib/interlok-boot.jar)`

Now you can do various api calls:

### CSV to XML using com.adaptris.csv.transform.CsvToXml

```
$ curl -s -XPOST --data-binary "@src/test/resources/example-3-lines.csv" "http://localhost:8081/csv-to/xml"
```

request body:
```csv
Issue Type,Priority,Issue key,Summary,Status,Created,Updated,Creator
Story,Low,INTERLOK-3120,"UI Optional Component - Host the icons in a remote location (nexus, git?)",Open,05/01/2020 07:03,10/02/2021 02:52,Sebastien B
Story,Normal,INTERLOK-3121,UI Config - Support README.md file in the config page,Closed,05/01/2020 08:48,06/08/2020 04:54,Paul H
Story,Normal,INTERLOK-3123,Bump google-cloud-pubsub from 1.98.0 to 1.102.0,Closed,06/01/2020 08:28,09/06/2020 08:10,Lewin C
```

response: 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<csv-xml>
   <record>
      <Issue_Type>Story</Issue_Type>
      <Priority>Low</Priority>
      <Issue_key>INTERLOK-3120</Issue_key>
      <Summary>UI Optional Component - Host the icons in a remote location (nexus, git?)</Summary>
      <Status>Open</Status>
      <Created>05/01/2020 07:03</Created>
      <Updated>10/02/2021 02:52</Updated>
      <Creator>Sebastien B</Creator>
   </record>
   <record>
      <Issue_Type>Story</Issue_Type>
      <Priority>Normal</Priority>
      <Issue_key>INTERLOK-3121</Issue_key>
      <Summary>UI Config - Support README.md file in the config page</Summary>
      <Status>Closed</Status>
      <Created>05/01/2020 08:48</Created>
      <Updated>06/08/2020 04:54</Updated>
      <Creator>Paul H</Creator>
   </record>
   <record>
      <Issue_Type>Story</Issue_Type>
      <Priority>Normal</Priority>
      <Issue_key>INTERLOK-3123</Issue_key>
      <Summary>Bump google-cloud-pubsub from 1.98.0 to 1.102.0</Summary>
      <Status>Closed</Status>
      <Created>06/01/2020 08:28</Created>
      <Updated>09/06/2020 08:10</Updated>
      <Creator>Lewin C</Creator>
   </record>
</csv-xml>
```


### CSV to XML using com.adaptris.csv.transform.StreamingCsvToXml

```
$ curl -s -XPOST --data-binary "@src/test/resources/example-586-lines.csv" "http://localhost:8081/streaming-csv-to/xml"
```

request body:
```csv
Issue Type,Priority,Issue key,Summary,Status,Created,Updated,Creator
Story,Low,INTERLOK-3120,"UI Optional Component - Host the icons in a remote location (nexus, git?)",Open,05/01/2020 07:03,10/02/2021 02:52,Sebastien B
Story,Normal,INTERLOK-3121,UI Config - Support README.md file in the config page,Closed,05/01/2020 08:48,06/08/2020 04:54,Sebastien B
Improvement,Normal,INTERLOK-3122,UI - Update Copyrights year to 2020,Closed,05/01/2020 09:25,05/02/2020 02:37,Sebastien B
Story,Normal,INTERLOK-3123,Bump google-cloud-pubsub from 1.98.0 to 1.102.0,Closed,06/01/2020 08:28,09/06/2020 08:10,Lewin C
Story,Lowest,INTERLOK-3128,UI Config Settings Editor - metadata preview should have information about where the md originated from,Open,07/01/2020 05:52,07/01/2020 05:52,Paul H
Story,Normal,INTERLOK-3129,Document the new multi-payload services,Closed,07/01/2020 06:08,12/02/2020 02:53,Aaron M
Story,Normal,INTERLOK-3130,Look into random test failures.,Closed,07/01/2020 07:08,18/02/2020 03:03,Aaron M
Improvement,Normal,INTERLOK-3131,Service Tester - AssertionResult has a uniqueId member which is never used,Closed,08/01/2020 06:39,07/02/2020 05:53,Sebastien B
Bug,Normal,INTERLOK-3132,ClassCastException with profiler when using custom services,Closed,13/01/2020 03:46,18/02/2020 06:44,Baldev Klair
Bug,Normal,INTERLOK-3133,Artifact Downloader - Cross-site Scripting (XSS),Closed,14/01/2020 01:24,18/03/2020 07:30,Sebastien B
Bug,Normal,INTERLOK-3134,Artifact Downloader - Denial of Service (DoS),Closed,14/01/2020 01:25,18/03/2020 07:33,Sebastien B
Bug,Normal,INTERLOK-3135,Profiler - Adding consumer events to map is wrong.,Closed,14/01/2020 10:48,18/02/2020 03:02,Aaron M
Improvement,Normal,INTERLOK-3136,interlok-cxf 'use-fallback-transformer' should default to true since Saxon 9.9 doesn't play nice,Closed,15/01/2020 05:36,04/02/2020 05:27,Lewin C
Story,Normal,INTERLOK-3137,Apache Http Producer - file as form-data,Open,15/01/2020 08:33,01/12/2020 04:11,Aaron M
Bug,Normal,INTERLOK-3138,UI - Warning and stacktrace for jxpaths class not found on startup ,Closed,15/01/2020 08:45,19/01/2020 08:01,Sebastien B
Improvement,Normal,INTERLOK-3139,UI Profiler - Zero messages displayed for some workflows,Open,16/01/2020 03:04,16/01/2020 03:04,Aaron M
Story,Normal,INTERLOK-3140,Profiler - Test the profiler running in docker,Closed,16/01/2020 03:07,12/02/2020 08:20,Aaron M
Story,Normal,INTERLOK-3141,Add DataInput + DataOutputParameters that are multi-payload aware,Closed,17/01/2020 03:47,04/02/2020 02:24,Lewin C
Bug,Critical,INTERLOK-3142,JMXMP Vulnerability ... needs verification / recommendations.,Closed,17/01/2020 06:06,22/07/2020 06:24,Lewin C
(...and so on)
```

response: 
```xml
<?xml version="1.0" encoding="UTF-8"?><csv-xml><record><Issue_Type>Story</Issue_Type><Priority>Low</Priority><Issue_key>INTERLOK-3120</Issue_key><Summary>UI Optional Component - Host the icons in a remote location (nexus, git?)</Summary><Status>Open</Status><Created>05/01/2020 07:03</Created><Updated>10/02/2021 02:52</Updated><Creator>Sebastien B</Creator></record><record><Issue_Type>Story</Issue_Type><Priority>Normal</Priority><Issue_key>INTERLOK-3121</Issue_key><Summary>UI Config - Support README.md file in the config page</Summary><Status>Closed</Status><Created>05/01/2020 08:48</Created><Updated>06/08/2020 04:54</Updated><Creator>Sebastien B</Creator></record><record><Issue_Type>Improvement</Issue_Type><Priority>Normal</Priority><Issue_key>INTERLOK-3122</Issue_key><Summary>UI - Update Copyrights year to 2020</Summary><Status>Closed</Status><Created>05/01/2020 09:25</Created><Updated>05/02/2020 02:37</Updated><Creator>Sebastien B</Creator></record><record><Issue_Type>Story</Issue_Type><Priority>Normal</Priority><Issue_key>INTERLOK-3123</Issue_key><Summary>Bump google-cloud-pubsub from 1.98.0 to 1.102.0</Summary><Status>Closed</Status><Created>06/01/2020 08:28</Created><Updated>09/06/2020 08:10</Updated><Creator>Lewin C</Creator></record><record><Issue_Type>Story</Issue_Type><Priority>Lowest</Priority><Issue_key>INTERLOK-3128</Issue_key><Summary>UI Config Settings Editor - metadata preview should have information about where the md originated from</Summary><Status>Open</Status><Created>07/01/2020 05:52</Created><Updated>07/01/2020 05:52</Updated><Creator>Paul H</Creator></record><record><Issue_Type>Story</Issue_Type><Priority>Normal</Priority><Issue_key>INTERLOK-3129</Issue_key><Summary>Document the new multi-payload services</Summary><Status>Closed</Status><Created>07/01/2020 06:08</Created><Updated>12/02/2020 02:53</Updated><Creator>Aaron M</Creator></record><record><Issue_Type>Story</Issue_Type><Priority>Normal</Priority><Issue_key>INTERLOK-3130</Issue_key><Summary>Look into random test failures.</Summary><Status>Closed</Status><Created>07/01/2020 07:08</Created><Updated>18/02/2020 03:03</Updated><Creator>Aaron M</Creator></record>
(...and so on)
```


### CSV to XML using com.adaptris.csv.transform.UncheckedCsvToXml

```
$ curl -s -XPOST --data-binary "@src/test/resources/unchecked-example.csv" "http://localhost:8081/unchecked-csv-to/xml"
```

request body:
```csv
Story,Normal,INTERLOK-3129,Document the new multi-payload services,Closed,07/01/2020 06:08,12/02/2020 02:53,Aaron M
Normal,INTERLOK-3131,Service Tester - AssertionResult has a uniqueId member which is never used,Closed
Bug,Normal,INTERLOK-3132,ClassCastException with profiler when using custom services,Closed,13/01/2020 03:46,18/02/2020 06:44,Baldev Klair
Bug,INTERLOK-3133,Artifact Downloader - Cross-site Scripting (XSS),Closed,Sebastien B
```

response: 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<csv-xml>
   <record>
      <csv-field-1>Story</csv-field-1>
      <csv-field-2>Normal</csv-field-2>
      <csv-field-3>INTERLOK-3129</csv-field-3>
      <csv-field-4>Document the new multi-payload services</csv-field-4>
      <csv-field-5>Closed</csv-field-5>
      <csv-field-6>07/01/2020 06:08</csv-field-6>
      <csv-field-7>12/02/2020 02:53</csv-field-7>
      <csv-field-8>Aaron M</csv-field-8>
   </record>
   <record>
      <csv-field-1>Normal</csv-field-1>
      <csv-field-2>INTERLOK-3131</csv-field-2>
      <csv-field-3>Service Tester - AssertionResult has a uniqueId member which is never used</csv-field-3>
      <csv-field-4>Closed</csv-field-4>
   </record>
   <record>
      <csv-field-1>Bug</csv-field-1>
      <csv-field-2>Normal</csv-field-2>
      <csv-field-3>INTERLOK-3132</csv-field-3>
      <csv-field-4>ClassCastException with profiler when using custom services</csv-field-4>
      <csv-field-5>Closed</csv-field-5>
      <csv-field-6>13/01/2020 03:46</csv-field-6>
      <csv-field-7>18/02/2020 06:44</csv-field-7>
      <csv-field-8>Baldev K</csv-field-8>
   </record>
   <record>
      <csv-field-1>Bug</csv-field-1>
      <csv-field-2>INTERLOK-3133</csv-field-2>
      <csv-field-3>Artifact Downloader - Cross-site Scripting (XSS)</csv-field-3>
      <csv-field-4>Closed</csv-field-4>
      <csv-field-5>Sebastien B</csv-field-5>
   </record>
</csv-xml>
```

### CSV to JSON (Array) using com.adaptris.core.transform.csvjson.CSVToJson (style=JSON_ARRAY)

```
$ curl -s -XPOST --data-binary "@src/test/resources/example-3-lines.csv" "http://localhost:8081/csv-to/json/array"
```

request body:
```csv
Issue Type,Priority,Issue key,Summary,Status,Created,Updated,Creator
Story,Low,INTERLOK-3120,"UI Optional Component - Host the icons in a remote location (nexus, git?)",Open,05/01/2020 07:03,10/02/2021 02:52,Sebastien B
Story,Normal,INTERLOK-3121,UI Config - Support README.md file in the config page,Closed,05/01/2020 08:48,06/08/2020 04:54,Paul H
Story,Normal,INTERLOK-3123,Bump google-cloud-pubsub from 1.98.0 to 1.102.0,Closed,06/01/2020 08:28,09/06/2020 08:10,Lewin C
```

response: 
```json
[ {
  "Issue Type" : "Story",
  "Priority" : "Low",
  "Issue key" : "INTERLOK-3120",
  "Summary" : "UI Optional Component - Host the icons in a remote location (nexus, git?)",
  "Status" : "Open",
  "Created" : "05/01/2020 07:03",
  "Updated" : "10/02/2021 02:52",
  "Creator" : "Sebastien B"
}, {
  "Issue Type" : "Story",
  "Priority" : "Normal",
  "Issue key" : "INTERLOK-3121",
  "Summary" : "UI Config - Support README.md file in the config page",
  "Status" : "Closed",
  "Created" : "05/01/2020 08:48",
  "Updated" : "06/08/2020 04:54",
  "Creator" : "Paul H"
}, {
  "Issue Type" : "Story",
  "Priority" : "Normal",
  "Issue key" : "INTERLOK-3123",
  "Summary" : "Bump google-cloud-pubsub from 1.98.0 to 1.102.0",
  "Status" : "Closed",
  "Created" : "06/01/2020 08:28",
  "Updated" : "09/06/2020 08:10",
  "Creator" : "Lewin C"
} ]
```

### CSV to JSON (Lines) using com.adaptris.core.transform.csvjson.CSVToJson (style=JSON_LINES)


```
curl -s -XPOST --data-binary "@src/test/resources/example-3-lines.csv" "http://localhost:8081/csv-to/json/lines"
```

request body:
```csv
Issue Type,Priority,Issue key,Summary,Status,Created,Updated,Creator
Story,Low,INTERLOK-3120,"UI Optional Component - Host the icons in a remote location (nexus, git?)",Open,05/01/2020 07:03,10/02/2021 02:52,Sebastien B
Story,Normal,INTERLOK-3121,UI Config - Support README.md file in the config page,Closed,05/01/2020 08:48,06/08/2020 04:54,Paul H
Story,Normal,INTERLOK-3123,Bump google-cloud-pubsub from 1.98.0 to 1.102.0,Closed,06/01/2020 08:28,09/06/2020 08:10,Lewin C
```

response: 
```json
{"Issue Type":"Story","Priority":"Low","Issue key":"INTERLOK-3120","Summary":"UI Optional Component - Host the icons in a remote location (nexus, git?)","Status":"Open","Created":"05/01/2020 07:03","Updated":"10/02/2021 02:52","Creator":"Sebastien B"}
{"Issue Type":"Story","Priority":"Normal","Issue key":"INTERLOK-3121","Summary":"UI Config - Support README.md file in the config page","Status":"Closed","Created":"05/01/2020 08:48","Updated":"06/08/2020 04:54","Creator":"Paul H"}
{"Issue Type":"Story","Priority":"Normal","Issue key":"INTERLOK-3123","Summary":"Bump google-cloud-pubsub from 1.98.0 to 1.102.0","Status":"Closed","Created":"06/01/2020 08:28","Updated":"09/06/2020 08:10","Creator":"Lewin C"}
```

### JSON to CSV using com.adaptris.core.transform.csvjson.JsonArrayToCSV

```
$ curl -s -XPOST --data-binary "@src/test/resources/example-3-records.json" "http://localhost:8081/json-to/csv"
```

request body:
```json
[ {
  "Issue Type" : "Story",
  "Priority" : "Low",
  "Issue key" : "INTERLOK-3120",
  "Summary" : "UI Optional Component - Host the icons in a remote location (nexus, git?)",
  "Status" : "Open",
  "Created" : "05/01/2020 07:03",
  "Updated" : "10/02/2021 02:52",
  "Creator" : "Sebastien B"
}, {
  "Issue Type" : "Story",
  "Priority" : "Normal",
  "Issue key" : "INTERLOK-3121",
  "Summary" : "UI Config - Support README.md file in the config page",
  "Status" : "Closed",
  "Created" : "05/01/2020 08:48",
  "Updated" : "06/08/2020 04:54",
  "Creator" : "Paul H"
}, {
  "Issue Type" : "Story",
  "Priority" : "Normal",
  "Issue key" : "INTERLOK-3123",
  "Summary" : "Bump google-cloud-pubsub from 1.98.0 to 1.102.0",
  "Status" : "Closed",
  "Created" : "06/01/2020 08:28",
  "Updated" : "09/06/2020 08:10",
  "Creator" : "Lewin C"
} ]
```

response: 

```csv
Issue Type,Priority,Issue key,Summary,Status,Created,Updated,Creator
Story,Low,INTERLOK-3120,"UI Optional Component - Host the icons in a remote location (nexus, git?)",Open,05/01/2020 07:03,10/02/2021 02:52,Sebastien B
Story,Normal,INTERLOK-3121,UI Config - Support README.md file in the config page,Closed,05/01/2020 08:48,06/08/2020 04:54,Paul H
Story,Normal,INTERLOK-3123,Bump google-cloud-pubsub from 1.98.0 to 1.102.0,Closed,06/01/2020 08:28,09/06/2020 08:10,Lewin C
```

### JSON to CSV using com.adaptris.core.transform.csvjson.JsonToFixedCSV

This api call only returns csv fields that have been specified (in the header parameter)

```
curl -s -XPOST --data-binary "@src/test/resources/example-3-records.json" "http://localhost:8081/json-to-fixed/csv?header=Priority,Issue+key,Status"
```

request body:

```json
[ {
  "Issue Type" : "Story",
  "Priority" : "Low",
  "Issue key" : "INTERLOK-3120",
  "Summary" : "UI Optional Component - Host the icons in a remote location (nexus, git?)",
  "Status" : "Open",
  "Created" : "05/01/2020 07:03",
  "Updated" : "10/02/2021 02:52",
  "Creator" : "Sebastien B"
}, {
  "Issue Type" : "Story",
  "Priority" : "Normal",
  "Issue key" : "INTERLOK-3121",
  "Summary" : "UI Config - Support README.md file in the config page",
  "Status" : "Closed",
  "Created" : "05/01/2020 08:48",
  "Updated" : "06/08/2020 04:54",
  "Creator" : "Paul H"
}, {
  "Issue Type" : "Story",
  "Priority" : "Normal",
  "Issue key" : "INTERLOK-3123",
  "Summary" : "Bump google-cloud-pubsub from 1.98.0 to 1.102.0",
  "Status" : "Closed",
  "Created" : "06/01/2020 08:28",
  "Updated" : "09/06/2020 08:10",
  "Creator" : "Lewin C"
} ]
```

response:

```csv
Priority,Issue key,Status
Low,INTERLOK-3120,Open
Normal,INTERLOK-3121,Closed
Normal,INTERLOK-3123,Closed
```

### CSV to database using com.adaptris.csv.jdbc.BatchInsertCSV

This api uses a derby database.

First, we have to initialise the database (create the table we're going to use):

```
$ curl -s http://localhost:8081/csv-db-init
Success - Database initialised
```

Now we can insert data into it:

```
$ curl -s -XPOST --data-binary "@src/test/resources/example-csv-db-records1.csv" "http://localhost:8081/csv-db-insert"
ISSUE_TYPE,PRIORITY,ISSUE_KEY,SUMMARY,STATUS,CREATED,UPDATED,CREATOR
Story,Low,INTERLOK-3120,"UI Optional Component - Host the icons in a remote location (nexus, git?)",Open,2006-09-10-00.00.00,2006-09-10-00.00.00,Seb B

$ curl -s -XPOST --data-binary "@src/test/resources/example-csv-db-records2.csv" "http://localhost:8081/csv-db-insert"
ISSUE_TYPE,PRIORITY,ISSUE_KEY,SUMMARY,STATUS,CREATED,UPDATED,CREATOR
Story,Normal,INTERLOK-3153,Add a CSV Aggregator,Closed,2020-01-23-03.29.00,2020-01-23-04.32.00,Lewin C
Story,Low,INTERLOK-3161,"UI Config - Enable the variable selector on key/value list inputs, before the add key/value action",Open,2020-01-28-03.56.00,2020-01-28-03.57.00,Paul H
Story,Normal,INTERLOK-3202,JCSMP - Update to use the new onAdaptrisMessage,Closed,2020-03-03-01.59.00,2020-04-27-04.09.00,Aaron M
```

Lets select the data we've inserted into the database:

```
$ curl -s http://localhost:8081/csv-db-select
```

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<results>
   <row>
      <issue_type>Story</issue_type>
      <priority>Low</priority>
      <issue_key>INTERLOK-3120</issue_key>
      <summary>UI Optional Component - Host the icons in a remote location (nexus, git?)</summary>
      <status>Open</status>
      <created>2006-09-10 00:00:00.0</created>
      <updated>2006-09-10 00:00:00.0</updated>
      <creator>Sebastien B</creator>
   </row>
   <row>
      <issue_type>Story</issue_type>
      <priority>Normal</priority>
      <issue_key>INTERLOK-3153</issue_key>
      <summary>Add a CSV Aggregator</summary>
      <status>Closed</status>
      <created>2020-01-23 03:29:00.0</created>
      <updated>2020-01-23 04:32:00.0</updated>
      <creator>Lewin C</creator>
   </row>
   <row>
      <issue_type>Story</issue_type>
      <priority>Low</priority>
      <issue_key>INTERLOK-3161</issue_key>
      <summary>UI Config - Enable the variable selector on key/value list inputs, before the add key/value action</summary>
      <status>Open</status>
      <created>2020-01-28 03:56:00.0</created>
      <updated>2020-01-28 03:57:00.0</updated>
      <creator>Paul H</creator>
   </row>
   <row>
      <issue_type>Story</issue_type>
      <priority>Normal</priority>
      <issue_key>INTERLOK-3202</issue_key>
      <summary>JCSMP - Update to use the new onAdaptrisMessage</summary>
      <status>Closed</status>
      <created>2020-03-03 01:59:00.0</created>
      <updated>2020-04-27 04:09:00.0</updated>
      <creator>Aaron M</creator>
   </row>
</results>
```

Finally, lets drop the table, seeing as this is only a test:

```
$ curl -s http://localhost:8081/csv-db-destroy
Success - Database destroyed
```




