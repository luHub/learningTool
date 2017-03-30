# LearningTool: 

Basically the idea goes as follows, a client server architecture:

+----------------------------------------------------+
|                                                    |
|                                                    |
|                                                    |
|                 Learning Tool                      |  Server
|                 Module                             |
|                                                    |
|                                                    |
+--------+--+------------+-+--------------+-+--------+
         ^  |            ^ |              ^ |
         |  |            | |              | |
         |  |            | |              | |
+----------------------------------------------------+
|        |  |            | |              | |        |
|        |  |            | |              | |        |
|        |  |            | |              | |        |
|  +-----+--v-----+ +----+-v------+ +-----+-v-----+  |
|  |              | |             | |             |  |
|  |Question      | |  Learn      | | Code        |  |
|  |Module        | |  Module     | | Runner      |  |   Client
|  |              | |             | |             |  |   JavaFX MVC
|  |              | |             | |             |  |
|  |              | |             | |             |  |
|  +--------------+ +-------------+ +-------------+  |
|  Workbook Module                                   |
+----------------------------------------------------+

1)Updates
---------

### Update 30/03/2017

-Release as minimun viable product.

2)Project Requirements
----------------------

3)Architecture
--------------

The project is divided into 3 layers each layer contains 
* All persistance layer is contained in learning tool module [https://github.com/luHub/learningTool]   
* MVC Layer: Only the MVC for the Question Creator.

4)Work Methodology
------------------- 

* TDD 
* "Rocket Launch Method": Basically it is lauched together as a Space Rocket and later it is decoupled, it will work because the only concern is to decouple certain points as the solution scales.

5)Copyright Information
----------------------- 
Code is under [https://www.apache.org/licenses/LICENSE-2.0.txt]
