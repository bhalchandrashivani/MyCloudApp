#!/bin/bash
echo "CREATING STACK"
stackName=$1

echo "enter Travis user name"
read user

echo "Enter domain name for codedeploy s3 bucket"
read cname

#echo "Enter EC2 Deploy Tag"
#read ectag

echo "Enter Account Number"
read acnumber

d=code-deploy.$cname.me
webt=web-app.$cname.me

echo $vpcTag
stackId=$(aws cloudformation create-stack --stack-name $stackName --template-body file://csye6225-cf-ci-cd.json --capabilities CAPABILITY_NAMED_IAM --parameters ParameterKey=usernameTag,ParameterValue=$user ParameterKey=S3BucketTag,ParameterValue=$d ParameterKey=AccTag,ParameterValue=$acnumber ParameterKey=webTag,ParameterValue=$webt --output text)

echo $stackIdNAMW

if [ -z $stackId ]; then
    echo 'Error occurred.Dont proceed. TERMINATED'
else
    aws cloudformation wait stack-create-complete --stack-name $stackId
    echo "STACK CREATION COMPLETE."
fi
