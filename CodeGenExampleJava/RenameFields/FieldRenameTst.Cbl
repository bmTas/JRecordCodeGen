       01  dup-name-1.
           03 record-type     pic x.
           03  field-1               pic x(20).
           03  run-date.
               05 year               pic 9(4).
               05 month              pic 9(2).
               05 day                pic 9(2).
           03  Birth-details.
               05 City               pic x(20).
               05 xdate.
                   07 year           pic 9(4).
                   07 month          pic 9(2).
                   07 day            pic 9(2).
           03  Death-Details.
               05 City               pic x(20).
               05 xdate.
                   07 year           pic 9(4).
                   07 month          pic 9(2).
                   07 day            pic 9(2).
           03  dups.
               09  Array-115          pic 9(5).
           03  Arrays.
               05 Array-111.
                  07  Array-112.
                      09  Array-114   pic xxx occurs 5.
                      09  Array-117   pic xxx occurs 5.
             05 Array-121.
                  07  Array-112 occurs 7.
                      09  Array-114   pic xxx occurs 5.
                      09  Array-115   pic xxx occurs 6.
       01  dup-name-2.
           03 record-type     pic x.
           03  field-1               pic x(20).
           03  run-date.
               05 year               pic 9(4).
               05 month              pic 9(2).
               05 day                pic 9(2).
           03  Birth-details.
               05 City               pic x(20).
               05 xdate.
                   07 year           pic 9(4).
                   07 month          pic 9(2).
                   07 day            pic 9(2).
           03  Death-Details.
               05 City               pic x(20).
               05 xdate.
                   07 year           pic 9(4).
                   07 month          pic 9(2).
                   07 day            pic 9(2).

