# AutoPhoneCaller

![ic_auto_phone](https://user-images.githubusercontent.com/46284863/128420454-add7064e-00ad-412e-9bf6-e7dcf2ce2890.png)  
![android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)

## Introduction
AutoPhoneCaller is an application which allows you to trigger your phone to make a phone call based on appropriate code word via SMS and caller ID, as long as the app running in the backgournd (and not terminated by the phone owner).
Since I couldn't find a simple application with these capabilities, i thought to myself it will be cool to automate this process for me instead
of asking a friend to make the phone call himself (or to make the phone call myself), and that's how the idea was born.  

![appPicture](https://user-images.githubusercontent.com/46284863/128420251-492f948a-b90f-4c3a-b489-ced8b9425b11.png)

## How it works
1. Configure a target phone number - this number will be dialed by the phone which installed the app, once it receives SMS from one of the source numbers.
2. Configure a source phone number - an SMS from this number with the appropriate code word, will trigger a phone call from the app owner to the target number.
3. Configure a code word - only this code word will trigger a phone call.
