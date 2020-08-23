  Feature: Stack Overflow Badges API
  This Feature test the stack overflow's Badges feature using API
  
  @APITest
  Scenario Outline: Retreive Badges with valid inputs
    Given The Stack overflow "Badges" API with paramaters <page> <pagesize> <fromdate> <todate> <inname> <min> <max> <id> <site>
     When user calls "GetBadgesAPI" with "GET" http request
     Then the API call get successfull with status code <expectedstatus>
      And "name" in response body is <inname>
      And "badge_type" in response body is "tag_based"
      And "rank" in response body is <expectedrank>
      And "award_count" in the response body is of type "number"
      And "badge_id" in the response body is of type "number"
      And "link" in the response body is of type "link"
      And "name" in the response body is of type "string_with_special_characters"
  
    Examples: 
      | page | pagesize | fromdate   | todate     | inname     | min    | max    | id   | site          | expectedstatus | expectedrank | 
      | 1    | 1        | 2020-08-01 | 2020-08-30 | gnuradio   | bronze | NULL   | NULL | stackoverflow | 200            | bronze       | 
      | 1    | 10       | NULL       | NULL       | regression | silver | silver | NULL | stackoverflow | 200            | silver       | 
      | 1    | 10       | NULL       | NULL       | regression | NULL   | gold   | NULL | stackoverflow | 200            | gold         | 
  
  @APITest
  Scenario Outline: Retreive Badges with invalid inputs
    Given The Stack overflow "Badges" API with paramaters <page> <pagesize> <fromdate> <todate> <inname> <min> <max> <id> <site>
     When user calls "GetBadgesAPI" with "GET" http request
     Then the API call get successfull with status code <expectedstatus>
      And "has_more" in response body is <hasmore>
      And "error_id" in response body is <errorid>
      And "error_message" in response body is <errormessage>
      And "error_name" in response body is <errorname>
      And "rank" not in response body is <expectedrank>
  
    Examples: 
      | page | pagesize | fromdate   | todate     | inname | min  | max  | id   | site          | expectedstatus | expectedrank | errorid | errormessage                         | errorname     | hasmore | 
      | 1.1  | 1        | 2020-08-01 | 2020-08-30 | NULL   | NULL | NULL | NULL | stackoverflow | 400            | NULL         | 400     | page                                 | bad_parameter | NULL    | 
      | 1    | 10.1     | 2020-08-01 | 2020-08-30 | NULL   | NULL | NULL | NULL | stackoverflow | 400            | bronze       | 400     | pagesize                             | bad_parameter | NULL    | 
      | -1   | 10       | 2020-13-01 | 2020-08-30 | NULL   | NULL | NULL | NULL | stackoverflow | 400            | bronze       | 400     | page                                 | bad_parameter | NULL    | 
      | 1    | -10      | 2020-13-01 | 2020-08-30 | NULL   | NULL | NULL | NULL | stackoverflow | 200            | bronze       | NULL    | NULL                                 | NULL 				 | true    | 
      | 1    | 10       | NULL       | NULL       | NULL   | NULL | NULL | NULL | abdcfdfg      | 400            | bronze       | 400     | No site found for name `abdcfdfg`    | bad_parameter | NULL    | 
      | 1    | 10       | NULL       | NULL       | NULL   | NULL | NULL | NULL | 11122332232   | 400            | bronze       | 400     | No site found for name `11122332232` | bad_parameter | NULL    | 
  
  @APITest
  Scenario Outline: Retreive Badges By Tags with valid inputs
    Given The Stack overflow "Badges" API with paramaters <page> <pagesize> <fromdate> <todate> <inname> <min> <max> <id> <site>
     When user calls "GetBadgesByTagAPI" with "GET" http request
     Then the API call get successfull with status code <expectedstatus>
      And "name" in response body is <inname>
      And "badge_type" in response body is "tag_based"
      And "rank" in response body is <expectedrank>
      And "award_count" in the response body is of type "number"
      And "badge_id" in the response body is of type "number"
      And "link" in the response body is of type "link"
      And "name" in the response body is of type "string_with_special_characters"
  
    Examples: 
      | page | pagesize | fromdate   | todate     | inname     | min    | max    | id   | site          | expectedstatus | expectedrank | 
      | 1    | 1        | 2020-08-01 | 2020-08-30 | gnuradio   | bronze | NULL   | NULL | stackoverflow | 200            | bronze       | 
      | 1    | 10       | NULL       | NULL       | regression | silver | silver | NULL | stackoverflow | 200            | silver       | 
      | 1    | 10       | NULL       | NULL       | firebird   | NULL   | gold   | NULL | stackoverflow | 200            | gold         | 
  
  @APITest
  Scenario Outline: Retreive Badges By Tags with invalid inputs
    Given The Stack overflow "Badges" API with paramaters <page> <pagesize> <fromdate> <todate> <inname> <min> <max> <id> <site>
     When user calls "GetBadgesByTagAPI" with "GET" http request
     Then the API call get successfull with status code <expectedstatus>
      And "has_more" in response body is <hasmore>
      And "error_id" in response body is <errorid>
      And "error_message" in response body is <errormessage>
      And "error_name" in response body is <errorname>
      And "rank" not in response body is <expectedrank>
  
    Examples: 
      | page | pagesize | fromdate | todate | inname   | min    | max    | id   | site          | expectedstatus | errorid | errormessage | errorname     | hasmore | expectedrank | 
      | 1    | 1        | NULL     | NULL   | NULL     | NULL   | name   | NULL | stackoverflow | 400            | 400     | max          | bad_parameter | NULL    | NULL         | 
      | 1    | 1        | NULL     | NULL   | NULL		 | NULL   | NULL   | NULL | stackoverflow | 200            | NULL    | NULL         | NULL          | false   | NULL         | 
      | 1    | 1        | NULL     | NULL   | NULL     | silver | silver | NULL | stackoverflow | 200            | NULL    | NULL         | NULL          | true    | gold         | 
  
  @APITest
  Scenario Outline: Retreive Badges By Id with valid inputs
    Given The Stack overflow "Badges" API with paramaters <page> <pagesize> <fromdate> <todate> <inname> <min> <max> <id> <site>
     When user calls "GetBadgesByIdAPI" with "GET" http request
     Then the API call get successfull with status code <expectedstatus>
      And "name" in response body is <inname>
      And "badge_type" in response body is <expectedbadgetype>
      And "rank" in response body is <expectedrank>
      And "award_count" in the response body is of type "number"
      And "badge_id" in the response body is of type "number"
      And "link" in the response body is of type "link"
      And "name" in the response body is of type "string_with_special_characters"
  
    Examples: 
      | page | pagesize | fromdate   | todate     | inname  | min    | max  | id | site          | expectedstatus | expectedrank | expectedbadgetype | 
      | 1    | 1        | 2020-08-01 | 2020-08-30 | Teacher | bronze | NULL | 1  | stackoverflow | 200            | bronze       | named             | 
  
  @APITest
  Scenario Outline: Retreive Badges By Id with invalid inputs
    Given The Stack overflow "Badges" API with paramaters <page> <pagesize> <fromdate> <todate> <inname> <min> <max> <id> <site>
     When user calls "GetBadgesByIdAPI" with "GET" http request
     Then the API call get successfull with status code <expectedstatus>
      And "has_more" in response body is <hasmore>
      And "error_id" in response body is <errorid>
      And "error_message" in response body is <errormessage>
      And "error_name" in response body is <errorname>
  
    Examples: 
      | page | pagesize | fromdate | todate | inname | min  | max  | id   | site          | expectedstatus | errorid | errormessage                   | errorname     | hasmore | 
      | 1    | 1        | NULL     | NULL   | NULL   | NULL | NULL | NULL | stackoverflow | 200            | NULL    | NULL                           | NULL          | NULL    | 
      | 1    | 1        | NULL     | NULL   | NULL   | NULL | NULL | ABCD | stackoverflow | 400            | 404     | no method found with this name | no_method     | NULL    | 
      | 1    | 1        | NULL     | NULL   | NULL   | NULL | NULL | "  " | stackoverflow | 400            | 400     | page                           | bad_parameter | NULL    | 
  
  @APITest
  Scenario Outline: Retreive Badges By Recipients with valid inputs
    Given The Stack overflow "Badges" API with paramaters <page> <pagesize> <fromdate> <todate> <inname> <min> <max> <id> <site>
     When user calls "GetBadgesByRecipientsAPI" with "GET" http request
     Then the API call get successfull with status code <expectedstatus>
      And user details are present in response body
      And "badge_type" not in response body is "NULL"      
      And "rank" in the response body is of type "string"
      And "badge_id" in the response body is of type "number"
      And "link" in the response body is of type "link"
      And "name" in the response body is of type "string_with_special_characters"
  
    Examples: 
      | page | pagesize | fromdate   | todate     | inname | min  | max  | id   | site          | expectedstatus | 
      | 1    | 1        | 2020-08-01 | 2020-08-30 | NULL   | NULL | NULL | NULL | stackoverflow | 200            | 
      | 1    | 10       | NULL       | NULL       | NULL   | NULL | NULL | NULL | stackoverflow | 200            | 
  
  @APITest
  Scenario Outline: Retreive Badges By Recipients with invalid inputs
    Given The Stack overflow "Badges" API with paramaters <page> <pagesize> <fromdate> <todate> <inname> <min> <max> <id> <site>
     When user calls "GetBadgesByRecipientsAPI" with "GET" http request
     Then the API call get successfull with status code <expectedstatus>
      And "error_id" in response body is <errorid>
      And "error_message" in response body is <errormessage>
      And "error_name" in response body is <errorname>
  
    Examples: 
      | page | pagesize | fromdate | todate | inname | min  | max  | id   | site          | expectedstatus | errorid | errormessage | errorname     | 
      | -1   | 1        | NULL     | NULL   | NULL   | NULL | NULL | NULL | stackoverflow | 400            | 400     | page         | bad_parameter | 
  
  @APITest
  Scenario Outline: Retreive Badges By Recipients Id with valid inputs
    Given The Stack overflow "Badges" API with paramaters <page> <pagesize> <fromdate> <todate> <inname> <min> <max> <id> <site>
     When user calls "GetBadgesByRecipientsIdAPI" with "GET" http request
     Then the API call get successfull with status code <expectedstatus>
      And user details are present in response body
      And "badge_id" in response body is <expectedid>
      And "badge_type" not in response body is "NULL"      
      And "rank" in the response body is of type "string"
      And "badge_id" in the response body is of type "number"
      And "link" in the response body is of type "link"
      And "name" in the response body is of type "string_with_special_characters"
  
    Examples: 
      | page | pagesize | fromdate | todate | inname | min  | max  | id                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  | site          | expectedstatus | expectedid | 
      | 1    | 1        | NULL     | NULL   | NULL   | NULL | NULL | 5893                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                | stackoverflow | 200            | 5893       | 
      | 1    | 10       | NULL     | NULL   | NULL   | NULL | NULL | 5898;5899;5900;5901;5902;5903;5904;5905;5906;5907;5908;5909;5910;5911;5912;5913;5914;5915;5916;5917;5918;5919;5920;5921;5922;5923;5924;5925;5926;5927;5928;5929;5930;5931;5932;5933;5934;5935;5936;5937;5938;5939;5940;5941;5942;5943;5944;5945;5946;5947;5948;5949;5950;5951;5952;5953;5954;5955;5956;5957;5958;5959;5960;5961;5962;5963;5964;5965;5966;5967;5968;5969;5970;5971;5972;5973;5974;5975;5976;5977;5978;5979;5980;5981;5982;5983;5984;5985;5986;5987;5988;5989;5990;5991;5992;5993;5994;5995;5996;5997 | stackoverflow | 200            | 5913       | 
  
  @APITest
  Scenario Outline: Retreive Badges By Recipients Id with invalid inputs
    Given The Stack overflow "Badges" API with paramaters <page> <pagesize> <fromdate> <todate> <inname> <min> <max> <id> <site>
     When user calls "GetBadgesByRecipientsIdAPI" with "GET" http request
     Then the API call get successfull with status code <expectedstatus>
      And "error_id" in response body is <errorid>
      And "error_message" in response body is <errormessage>
      And "error_name" in response body is <errorname>
  
    Examples: 
      | page | pagesize | fromdate | todate | inname | min  | max  | id                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | site          | expectedstatus | errorid | errormessage                   | errorname     | 
      | -1   | 1        | NULL     | NULL   | NULL   | NULL | NULL | 5913                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | stackoverflow | 400            | 400     | page                           | bad_parameter | 
      | 1    | 1        | NULL     | NULL   | NULL   | NULL | NULL | 5913,5899                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                | stackoverflow | 400            | 404     | no method found with this name | no_method     | 
      | 1    | 1        | NULL     | NULL   | NULL   | NULL | NULL | 5898;5899;5900;5901;5902;5903;5904;5905;5906;5907;5908;5909;5910;5911;5912;5913;5914;5915;5916;5917;5918;5919;5920;5921;5922;5923;5924;5925;5926;5927;5928;5929;5930;5931;5932;5933;5934;5935;5936;5937;5938;5939;5940;5941;5942;5943;5944;5945;5946;5947;5948;5949;5950;5951;5952;5953;5954;5955;5956;5957;5958;5959;5960;5961;5962;5963;5964;5965;5966;5967;5968;5969;5970;5971;5972;5973;5974;5975;5976;5977;5978;5979;5980;5981;5982;5983;5984;5985;5986;5987;5988;5989;5990;5991;5992;5993;5994;5995;5996;5997;5998 | stackoverflow | 400            | 400     | ids                            | bad_parameter | 
  