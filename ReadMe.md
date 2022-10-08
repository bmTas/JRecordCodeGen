# JRecord CodeGen

## Summary
 **Purpose:** Generate **Java~JRecord** code to Read/Write *Cobol Data files* from a **Cobol Copybook**.
 **License:** GPL
 **Queries:** Use the JRecord Forums.
 **Problems:** Use either the JRecord Forums or JRecord Tickets.
 
 **Future:** Future development will be based on the level of interest / feedback. So if you find this program is
 useful to you, please Let me know !!!, provide feedback and get involved !!! also documentation would be useful.

Changes in this release include:

* Support for duplicate field names in most Templates.
* Adding code to analyse Cobol-data-files and work out the attributes.

## CodeGen's purpose

CodeGen's purpose is to read a *Cobol copybook* (and optionally s *Cobol Data File*) and generate
*Java~JRecord* code read/write convert the file

For example, the **POJO** template will convert a Cobol Copybook, e.g.

~~~cobol
              03  DTAR020-KCODE-STORE-KEY.                                      
                  05 DTAR020-KEYCODE-NO      PIC X(08).                         
                  05 DTAR020-STORE-NO        PIC S9(03)   COMP-3.               
              03  DTAR020-DATE               PIC S9(07)   COMP-3.               
              03  DTAR020-DEPT-NO            PIC S9(03)   COMP-3.               
              03  DTAR020-QTY-SOLD           PIC S9(9)    COMP-3.               
              03  DTAR020-SALE-PRICE         PIC S9(9)V99 COMP-3.             
~~~

The **Pojo** template will create a java *pojo* class and java/jrecord code
to convert the java *pojo* to/from the Cobol Data

~~~java
public class LineDtar020Pojo {

    private String keycodeNo;
    private short storeNo;
    private int date;
    private short deptNo;
    private int qtySold;
    private BigDecimal salePrice;
    
    public String getKeycodeNo() {
        return keycodeNo;
    }
     
    public void setKeycodeNo(String value) {
        this.keycodeNo = value;
    }
    
    public short getStoreNo() {
        return storeNo;
    }
    
    public void setStoreNo(short value) {
        this.storeNo = value;
    }
    
    public int getDate() {
        return date;
    }
    
    public void setDate(int value) {
        this.date = value;
    }
    
    public short getDeptNo() {
        return deptNo;
    }
     
    public void setDeptNo(short value) {
        this.deptNo = value;
    }
    
    public int getQtySold() {
        return qtySold;
    }
    
    public void setQtySold(int value) {
        this.qtySold = value;
    }
    
    public BigDecimal getSalePrice() {
        return salePrice;
    }
   
    public void setSalePrice(BigDecimal value) {
        this.salePrice = value;
    }
}
~~~


## Calling CodeGen Template interface

**Java Interface**

There is a builder style interface to CodeGen. In the following example, the **analyseDataFileSetAttributes** codegen to try and determine file
attributes from the sample data file.

```java
     CodeGenInterface.TEMPLATES.newTempateBuilder("DTAR020.cbl")
             .setDataFile("DTAR020.bin")
             .analyseDataFileSetAttributes()
             .setTemplate(StandardTemplates.STANDARD)
             .setPackageName("dtar020.standard")
             .setOutputDirectory("G:\\Temp\\Gen\\TestCodeGen\\dtar020")
             .generateJava()
             .writeTemplateBuilderCode("build"); 
```


There are examples of generating Java-JRecord code using the **standard** Template in the CodeGenExample/standard directory.

Basically to generate Java code for a Mainframe EBCDIC Fixed-Record-Length  file:


**Using Batch Interface:**


      java -jar ../../lib/JRecordCodeGen.jar  ^
          -Template           standard ^
               -package            example.ioBuilder.dtar020 ^ 
               -Schema             DTAR020.cbl   ^
                 -FileOrganisation FixedWidth ^
                 -font cp037 ^
                 -DropCopybookName   true ^
               -outputDirectory    DTAR020




The generated **FieldName** class is
 
```java
       public class FieldNamesDtar020 {
     
         public static final RecordDtar020 RECORD_DTAR020 = new RecordDtar020();
     
         public static class RecordDtar020 {
            public final String keycodeNo = "KEYCODE-NO";
            public final String storeNo = "STORE-NO";
            public final String date = "DATE";
            public final String deptNo = "DEPT-NO";
            public final String qtySold = "QTY-SOLD";
            public final String salePrice = "SALE-PRICE";
         }
       }     
``` 


The generated **read** code is

```java 
         AbstractLine line;
         int lineNum = 0;
 
         try {
             ICobolIOBuilder iob = JRecordInterface1.COBOL
                                        .newIOBuilder(copybookName)
                                            .setFont("CP037")
                                            .setFileOrganization(Constants.IO_FIXED_LENGTH)
                                            .setSplitCopybook(CopybookLoader.SPLIT_NONE)
                                            .setDropCopybookNameFromFields(true)
                                        ;  
 
 
             FieldNamesDtar020.RecordDtar020 rDtar020 = FieldNamesDtar020.RECORD_DTAR020;
             AbstractLineReader reader = iob.newReader(salesFile);
             while ((line = reader.read()) != null) {
                 lineNum += 1;
                 System.out.println(
                               line.getFieldValue(rDtar020.keycodeNo).asString()
                       + " " + line.getFieldValue(rDtar020.storeNo).asString()
                       + " " + line.getFieldValue(rDtar020.date).asString()
                       + " " + line.getFieldValue(rDtar020.deptNo).asString()
                       + " " + line.getFieldValue(rDtar020.qtySold).asString()
                       + " " + line.getFieldValue(rDtar020.salePrice).asString()
                    );
             }
 
             reader.close();
         } catch (Exception e) {
         }
```


### Multi Record Example


In the following example

* The **setDataFile** defines a sample data file. This is used to 
work out file attributes and Record-type attributes.
* If you want CodeGen to work out the file attributes from a sample file use the **analyseDataFileSetAttributes** method.
* The **createRecordDecider** tells CodeGen to try and create a *record mapping* from the Cobol Data to one
of the records in the Cobol Copybook. There is no guarantee the system will succeed though.


```java 
         CodeGenInterface.TEMPLATES.newTempateBuilder("amsPoDownload.cbl")
                 .setDataFile("/home/bruce/.RecordEditor/HSQLDB/SampleFiles/Ams_PODownload_20041231.txt")
                 .setCopybookSplitOption(CopybookSplit.SPLIT_01_LEVEL)
                 .analyseDataFileSetAttributes()
                 .createRecordDecider()
                 .setTemplate(StandardTemplates.POJO)
                 .setPackageName("amsPoDownload.pojo")
                 .setOutputDirectory("/home/bruce/work/temp/codeGenJavaInterface/amsPoDownload/v1")
                 .generateJava()
                 .writeTemplateBuilderCode("build");
```

or with all attributes hard coded

```java 
         ISingleFieldDecider deciderDescription = JRecordInterface1.RECORD_DECIDER_BUILDER
                 .singleFieldDeciderBuilder("Record-Type", false)
                     .addRecord("h1", "PO-Record")
                     .addRecord("d1", "Product-Record")
                     .addRecord("s1", "Location-Record")
                 .build();
 
 
         CodeGenInterface.TEMPLATES.newTempateBuilder("amsPoDownload.cbl")
             .setCobolDialect(CobolDialects.MAINFRAME)
             .setFileOrganisation(FileOrganisation.TEXT)
             .setCopybookSplitOption(CopybookSplit.SPLIT_01_LEVEL)
             .addRecordDecider(deciderDescription)
             .setTemplate(StandardTemplates.POJO)
             .setPackageName("amsPoDownload.pojo")
             .setOutputDirectory("G:\\Temp\\CodeGen\\amspo")
             .generateJava();

``` 




## CodeGen Walker Interface

### Standard Walker Interface

```java 
         ICobolIOBuilder ioBuilder = JRecordInterface1.COBOL
                 .newIOBuilder("DTAR020.cbl")
                         .setFileOrganization(IFileStructureConstants.IO_FIXED_LENGTH)
                         .setFont("cp037");
         
         ISchemaWalkerInterface walker = CodeGenInterface.WALKER;
         walker.newBuilder(ioBuilder)
                     .setOutputDirectory("/home/bruce/work/JRecord/CodeGen/walker")
                     .setCobolDataClassPackageName("test.walker.Builder.GenerateCode.data")
                     .setReadClassGenerateParameters(walker.newClassDetailsParam()
                                                         .setPackageName("test.walker.Builder.GenerateCode.example"))
                     .setWriteClassGenerateParameters(walker.newClassDetailsParam()
                                                         .setPackageName("test.walker.Builder.GenerateCode.example"))
                 .generate();
```


This will generate a data class like:

```java 
 public class Dtar020 implements IGetByteData {
     private static IoProvider<Dtar020, Dtar020> provider = new IoProvider<Dtar020, Dtar020>(
             BasicFileSchema.newFixedSchema(IFileStructureConstants.IO_FIXED_LENGTH_RECORDS, true, 27, "cp037"), 
             new IDeserializer<Dtar020>() {
                 @Override public Dtar020 deserialize(byte[] rec) { return new Dtar020(rec); }
             });
     public static IoProvider<Dtar020, Dtar020> getIoProvider() { return provider;};
 
     private byte[] cobolData;
 
     public Dtar020() {
         cobolData = new byte[27];
     }
 
     public Dtar020(byte[] cobolData) {
         this.cobolData = cobolData; 
     }
 
     @Override public byte[] getData() {     return cobolData;   }
 
     @Override public void setData(byte[] data) {        this.cobolData = data;  }
 
     public final IStringField keycodeNo = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-KEYCODE-NO", Type.ftChar, 1, 8, 0, "cp037"));
     public final IIntField storeNo = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-STORE-NO", Type.ftPackedDecimal, 9, 2, 0, "cp037"));
     public final IIntField date = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-DATE", Type.ftPackedDecimal, 11, 4, 0, "cp037"));
     public final IIntField deptNo = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-DEPT-NO", Type.ftPackedDecimal, 15, 2, 0, "cp037"));
     public final IIntField qtySold = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-QTY-SOLD", Type.ftPackedDecimal, 17, 5, 0, "cp037"));
     public final IDecimalField salePrice = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-SALE-PRICE", Type.ftPackedDecimal, 22, 6, 2, "cp037"));
 }
```

and example generated code to read the file

```java 
         IReader<Dtar020> reader = Dtar020.getIoProvider().newReader("filename???");
         Dtar020 line;
         
         while ((line = reader.read()) != null) {
             System.out.println(""
                 + line.keycodeNo.asString() + "\t"
                 + line.storeNo.asInt() + "\t"
                 + line.date.asInt() + "\t"
                 + line.deptNo.asInt() + "\t"
                 + line.qtySold.asInt() + "\t"
                 + line.salePrice.asBigDecimal() + "\t"
             );
         }
         reader.close();
```

### User written Schema Walker

The Schema walker was provided to allow users to write there own Code Generation code in Java.

The schema walker walks the records and fields of a schema calls a  user supplied *listener* class to process each record/field.
This listener supplied class can generate code amongst other things.


The following walker example will list the records and fields in a Cobol copybook:

```java 
    ICobolIOBuilder ioBuilder = JRecordInterface1.COBOL
                 .newIOBuilder(TstIoBldrGen01.class.getResource("DTAR020.cbl").getFile())
                         .setFileOrganization(IFileStructureConstants.IO_FIXED_LENGTH)
                         .setFont("cp037");
         
         CodeGenInterface.WALKER.newSchemaWalker(ioBuilder)
             .walk(new SchemaWalkerAdapter() {
 
                 @Override public void startRecord(LayoutDef schema, RecordDef record) {
                     System.out.println("\n\t" + record.getCobolName());
                 }
 
                 String spaces = "                                  ";
                 @Override public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
                     Usage usage = fieldDtls.getCobolItem().getUsage();
                     System.out.println("\t\t" 
                             + (fieldDtls.getCobolName() + spaces).subSequence(0, 32)
                             + fieldDtls.getPos() + "\t" + fieldDtls.getLen() + "\t" + fieldDtls.getDecimal()
                             + "\t" + fieldDtls.getCobolItem().getPicture()
                             + "\t" + (usage == Usage.NONE ? "" : usage)
                             );
                 }
             });
```

There are several examples of *walker listener* classes in the codegen package, these are
some examples
* **Generate** a *java data* class for a Cobol copybook
* **Generate** a java program to read a Cobol data file   


                                                                   
## CodeGen Templates
                                                                     

The **CodeGen** program reads a Cobol-Copybook and Generates *Java~JRecord*
Code to read/write the Cobol-Data file. **CodeGen** is built around *Templates*.
A **CodeGen Template** is a directory of *Velocity Templates*   
with a **Generate.properties** control file. Each **CodeGen Template** generates one or more programs/files. 


The **templates I suggest looking at** are:

* **pojo** - will convert the Cobol Records to / from Java objects.
It can have problems if there are duplicate field names, redefines or complicated Cobol files
* **lineWrapper** - This creates a *Java Wrapper* around a
*JRecord~Line*. It is good when only accessing a few fields or the files is to complicated for
the **pojo**  template
* **standard** generates basic low-level JRecord code without any *java representation*.
This template **will** handle Cobol Copybooks the other templates **will not**.

 

                                                                                       
**CodeGen** code-templates include:

* **basic** - generate very basic JRecord Read/Write example code.
* **standard** - like the **basic** template only a Class holding **Cobol Field Names**
is generated and used. The initial release of **CodeGen** focuses on the **Standard** Template.
There are examples in the *CodeGenExample/standard* directory 
* **lineWrapper** - Is an extension on the *standard* template. It generates a *line-wrapper*
the has getter/setters for the Cobol fields.
* **lineWrapperPojo** - Is an extension on the *lineWrapper* template. It generates an interface for the *line-wrapper*
+ an alternative *Pojo* implementation of the line.
* pojo - Provides basic Pojo (plain old java objects) with readers / writers for the Pojo
* **schemaClass** An extension of *standard* template. It generates a full *schema* class. The 
Schema details can be either loaded from a **Cobol** copybook or **Created** at run time.
There are likely to be problems with *Occurs Depending*.
See **CodeGenExample/otherTemplates** for examples of generating **schemaClass** templates.
* **javaPojo** Generates a **Pojo / Javabean** class + reader, writer and schema classes.
This template currently supports single record files with no redefines (and no utf-8/16 files).
See **CodeGenExample/otherTemplates** for examples of generating **javaPojo** templates.



### Standard CodeGen Template
                                                                    
The **standard** is a fairly simple "Template", it generates 3 files
* A **field name** Class. This field name class holds all the *Cobol Field names* 
as java final variables. Rather than having to remember the *Cobol Field names*, you can then use Auto-Completion in your IDE. There will be issues
if there are duplicate field names.
* A sample *Read* program.
* A sample *Write* program.



### Line Wrapper Template

The **lineWrapper** is an extension of the **standard** "Template", it adds a LineWrapper class

* The **LineWrapper** class - this class has getter/setters methods for the Cobol Fields. These getter/setter call the getFieldValue(...) method
of the "wrapped" line.
* A **field name** Class. This field name class holds all the *Cobol Field names* 
as java final variables. Rather than having to remember the *Cobol Field names*, you can then use Auto-Completion in your IDE. There will be issues
if there are duplicate field names.
* A sample *Read* program.
* A sample *Write* program.



There are examples of generating Java-JRecord code using the **standard** Template in the *CodeGenExample/lineWrapper* directory.

Basically to generate Java code for a Mainframe EBCDIC Fixed-Record-Length  file:


To produce skeleton programs using Single record copybook (taken from *genDTAR027.bat*):
                                                                            
```
  java -jar ../../lib/JRecordCodeGen.jar  ^
                -Template          lineWrapper ^
               -package            lineWrapper.dtar027 ^ 
               -Schema             DTAR027.cbl   ^
                 -FileOrganisation FixedWidth ^
                 -font             cp037 ^
                 -DropCopybookName true ^
               -outputDirectory    DTAR027


```


The generated **FieldName** class is

```java
 public class FieldNamesDtar027 {
 
     public static final RecordDtar027 RECORD_DTAR027 = new RecordDtar027();
 
     public static class RecordDtar027 {
        public final String keycodeNo = "DTAR027-KEYCODE-NO";
        public final String qtySold = "DTAR027-QTY-SOLD";
        public final String salePrice = "DTAR027-SALE-PRICE";
     }
 } 
```
                                                                          
**Note:** This is exactly the same as fieldname class generated by *standard "Template"*.


The generated **LineWrapper** class is

```java
 public class LineDtar027JR implements IGetByteData {
     
     private AbstractLine line; 
 
     private static FieldNamesDtar027.RecordDtar027 fn
                    = FieldNamesDtar027.RECORD_DTAR027;
 
         
 
     public String getKeycodeNo() {
         return line.getFieldValue(fn.keycodeNo).asString();
     }
     
     public void setKeycodeNo(String value) {
         this.line.getFieldValue(fn.keycodeNo).set(value);
     }
 
     public int getQtySold() {
         return line.getFieldValue(fn.qtySold).asInt();
     }
     
     public void setQtySold(int value) {
         this.line.getFieldValue(fn.qtySold).set(value);
     }
 
     public BigDecimal getSalePrice() {
         return line.getFieldValue(fn.salePrice).asBigDecimal();
     }
     
     public void setSalePrice(BigDecimal value) {
         this.line.getFieldValue(fn.salePrice).set(value);
     }
 
 
         
 
 
     @Override
     public byte[] getData() {
         return line.getData();  
     }
 
     @Override
     public void setData(byte[] data) {
 
         if (line instanceof Line) {
             ((Line) line).setData(data);
         } else {
             throw new RuntimeException("Invalid line for setdata");
         }
     }
     
     public LineDtar027JR setLine(AbstractLine l) {
         line = l;
         return this;
     }
 }
```


The generated **read** code is

```java
             ICobolIOBuilder iob = JRecordInterface1.COBOL
                                        .newIOBuilder(copybookName)
                                            .setFont("cp037")
                                            .setFileOrganization(Constants.IO_FIXED_LENGTH)
                                            .setSplitCopybook(CopybookLoader.SPLIT_NONE)
                                        ;  
 
 
            LineDtar027JR lineDtar027JR = new LineDtar027JR();
  
         
            AbstractLineReader reader = iob.newReader(dataFile);
            while ((line = reader.read()) != null) {
                lineNum += 1;
                lineDtar027JR.setLine(line);
                System.out.println(
                               lineDtar027JR.getKeycodeNo() 
                       + " " + lineDtar027JR.getQtySold() 
                       + " " + lineDtar027JR.getSalePrice() 
                    );
             }
 
             reader.close();
```


### lineWrapperPojo Template


The **lineWrapperPojo** template is an extension of the **lineWrapper** template. The **Read**/**write** programs + the 
FieldName and Line**JR classes are very similar to the **LineWrapper** versions.   

What you need to understand with **lineWrapperPojo** template is the relationship between the
ILine... interface and it's two implementing classes. For DTAR027:

```

       **Interface                    Implementing class**
       
                       +---- **LineDtar027JR**   - Implemented as a wrapper around a
                       |     |           ^     JRecord Line 
                       |     |           | 
  **ILineDtar027Upd** -----+     | **toPojo()**  | **set(ILineDtar027 line)**                  
                       |     |           |
                       |     V           | 
                       +---- **LineDtar027Pojo** - Implemented using standard java
                                               types, it does not reference JRecord


```


To produce skeleton programs using Single record copybook (taken from *genDTAR027.bat*):

```
  java -jar ../../lib/JRecordCodeGen.jar  ^                              
               **-Template           lineWrapperPojo** ^
               -package            lineWrapperPojo.dtar027 ^ 
               -Schema             DTAR027.cbl   ^
                 -FileOrganisation FixedWidth ^
                 -font             cp037 ^
                 -DropCopybookName true ^
               -outputDirectory    DTAR027


```
        
For the Read, FieldName and LineDtar027JR classes see there versions in the *LineWrapper* template.

For IDtar027Pojo:

```java
 public interface IDtar027Pojo extends IDtar027Upd, IDtar027 { }

 public interface IDtar027 {
 
     public String getKeycodeNo();
     public int getQtySold();
     public BigDecimal getSalePrice();
 }
 

 public interface IDtar027Upd {
 
     public abstract void setKeycodeNo(String value);
     public abstract void setQtySold(int value);
     public abstract void setSalePrice(BigDecimal value);
 
     public abstract void set(IDtar027 value);
 }
```

Finally the LinePojo looks like:

```java
 public class LineDtar027Pojo implements IDtar027Pojo {
 
     private String keycodeNo;
     private int qtySold;
     private BigDecimal salePrice;
 
     @Override
     public String getKeycodeNo() {
         return keycodeNo;
     }
     
     @Override
     public void setKeycodeNo(String value) {
         this.keycodeNo = value;
     }
 
     @Override
     public int getQtySold() {
         return qtySold;
     }
     
     @Override
     public void setQtySold(int value) {
         this.qtySold = value;
     }
 
     @Override
     public BigDecimal getSalePrice() {
         return salePrice;
     }
     
     @Override
     public void setSalePrice(BigDecimal value) {
         this.salePrice = value;
     }
 
 
     public void set(IDtar027 value) {
         CodeDtar027
             .assignDtar027(
                 this, value);
     }
 }

```

### Pojo Template
The Pojo Template will generate a:

* **Pojo** class that holds the files data in *Java* form.
* **Conversion** classes to convert between Cobol and Java.
* **IoBuilder** class which will create *reader's* and *writer's*.
* Example read and Write programs.

#### Generated Pojo           

```java
 public class LineDtar027Pojo   {
 
     private String keycodeNo;
     private int qtySold;
     private BigDecimal salePrice;
    
     public String getKeycodeNo() {
         return keycodeNo;
     }
     
     public void setKeycodeNo(String value) {
         this.keycodeNo = value;
     }
 
     public int getQtySold() {
         return qtySold;
     }
     
     public void setQtySold(int value) {
         this.qtySold = value;
     }
 
     public BigDecimal getSalePrice() {
         return salePrice;
     }
     
     public void setSalePrice(BigDecimal value) {
         this.salePrice = value;
     }
 }
```


#### Generated Reading Example

```java
         IReader<LineDtar027Pojo> reader = null;
         try {
             IoBuilder<LineDtar027Pojo> iob = IoBuilderDtar027.newIoBuilder(copybookName);
             LineDtar027Pojo line;
 
             reader = iob.newReader(dataFile);        
             while ((line = reader.read()) != null) {
                 lineNum += 1;
                 
                System.out.println(
                               line.getKeycodeNo()
                       + " " + line.getQtySold()
                       + " " + line.getSalePrice()
                    );
             }
         } catch (IOException e) {
```



## CodeGen Batch Parameters


```
Program Options:                                       
    **-TemplateDirectory:** directory where user written templates are
    **-Template:**          Which template to generate
            **basic       -** Generate example code using JRecord IO Builders
            **standard    -** Generate example code using JRecord IO Builders + Field Name Class
            **schemaClass -** Generate example code using JRecord IO Builders + Schema details   
            **pojo        -** Generate pojo classes for each Cobol Record + IoBuilder
            **javaPojo    -** Generate java classes for each Cobol Record
            **pojoWithInterface -** Generate pojo classes (with interfaces) for each Cobol Record + IoBuilder
    **-Schema:**            Cobol copybook/Xml schema to generate code for
    **-package:**           Java Package Id
    **-loadSchema:**        Wether to generate a Schema (LayoutDetail) class or not
           **inLine       -** Create a SchemaClass in code
           **fromFile     -** Reload schema's from a file
           **both         -** Do both, create schema class and allow user to read from a file
    **-FileOrganisation:**  What sort of file will be read ???
           **Text         -** Standard Windows/Unix text file (single byte characterset)
           **FixedWidth   -** File where lines (records) are the same length no \n
           **Mainframe_VB -** Mainframe VB, file consists of <record-length><record-data>
    **-split:**             How to split the copybook up
           **None         -** No Copybook split
           **01           -** Split on 01
           **redefine     -** Split on redefine
           **highest      -** Hightest Level
    **-font:**              Font (characterset name
    **-DropCopybookName:**  Whether to Drop the copybook name from the start of field names
    **-loadSchema:**        Wether to generate a Schema (LayoutDetail) class or not
           **inLine       -** Create a SchemaClass in code
           **fromFile     -** Reload schema's from a file
           **both         -** Do both, create schema class and allow user to read from a file
    **-output**Directory:**   Output directory

    **-h -?:**</font>              List options 
```

 
## Changes

### Version 0.93


### Version 0.90.5

* Introducing a java interface (alternative to the batch interface). Going forward the java interface will
	be the prefered method of interfacing with **CodeGen**. The **CodeGenInterface** class as the main interface class to CodeGen.
* A new *Schema Walker* interface introduced to CodeGen
* Option to determine file options from a sample data file.
* Support of Cobol Occurs depending on clause in lineWraper and pojo templates
* Updates for the latest version of JRecord (0.95) 
* Option to try and determine file attributes from a sample data file.
* Add limited support to *RecordDecider* options.
* Option to generate a java Codegen generate program. Uses include
    * Convert existing CodeGen batch calls to the new java interface.
    * Create CodeGen java call from the **RecordEditor**
	  and **ReCsvEditor**.
    * Create a standalone CodeGen call when using file-Analysis options.
* Improve/add comments to the generated Java~JRecord code

