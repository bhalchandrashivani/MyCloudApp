#!/bin/bash
echo "Updating Stack"
stack_name=$1

echo "Enter Secure Key name"
read idRsa

echo "Enter name for s3 bucket"
read dname

fname=s3.csye6225-spring2018-$dname
tld=.me

echo $stack_name
subnetExportName1="csye6225-cloud-Networking-db-subnet1Id"
subnetExportName2="csye6225-cloud-Networking-db-subnet2Id"
stackId=$(aws cloudformation create-stack --stack-name $stack_name --template-body file://csye6225-cf-application.json --parameters ParameterKey=subnetExportName1,ParameterValue=$subnetExportName1 ParameterKey=subnetExportName2,ParameterValue=$subnetExportName2 ParameterKey=keyTag,ParameterValue=$idRsa ParameterKey=NameTag,ParameterValue=$stack_name ParameterKey=S3BucketTag,ParameterValue=$fname$dname$tld --query [StackId] --output text)
echo "Stack id is"
echo $stackId

if [ -z $stackId ]; then
    echo 'Error occurred.Dont proceed. TERMINATED'
else
    aws cloudformation wait stack-create-complete --stack-name $stack_name
    echo "STACK CREATION COMPLETE."
fi
