#!/bin/bash
stack_name=$1

NameTag=$stack_name

export ret=$(aws cloudformation create-stack --stack-name $stack_name --template-body file://csye6225-cf-application.json --parameters ParameterKey=NameTag,ParameterValue=$NameTag --query [StackId] --output text)

echo $ret

if [[ $ret = *$stack_name* ]]; then
   echo "Successful"
else
   echo "Failure"
fi
