#!/bin/bash

VPC_NAME="$1"
#echo "Creating VPC in '$AWS_REGION' region..."
#VPC_ID=$(aws ec2 create-vpc --cidr-block $VPC_CIDR --query 'Vpc.{VpcId:VpcId}' --output text --region $AWS_REGION)
#echo "VPC ID '$VPC_ID' CREATED in '$AWS_REGION' region."
#VPC_ID=$(aws ec2 describe-vpcs --querry "Vpcs[?Vpc" 

VPC_ID=$(aws ec2 describe-vpcs --filter "Name=tag:Name,Values=${VPC_NAME}" --query 'Vpcs[*].{id:VpcId}' --output text)
#IG_GATEWAY=$(aws ec2 describe-internet-gateways)
#IG_GATEWAY=$(aws ec2 describe-internet-gateways --filters "Name=attachment.vpc-id,Values=$VPC_ID" --query 'gateways[*].{id:Attachments[0].InternetGatewayId}' --output text)
IG_GATEWAY=$(aws ec2 describe-internet-gateways --filters "Name=attachment.vpc-id,Values=${VPC_ID}" --output text  | grep  igw| awk '{print $2}')
#ROUTE_TABLE_ID=$(aws ec2 describe-route-tables --filters "Name=association.route-table-id,Values=$VPC_ID")
#ROUTE_TABLE_ID=$(aws ec2 describe-route-tables --filters "Name=association.vpc-id,Values=${VPC_ID}" --output text)
ROUTE_TABLE_ID=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=$VPC_ID" --query 'RouteTables[?Associations[0].Main != `true`]' --output text | grep  rtb| awk '{print $1}')
#ROUTE_TABLE_ID2=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=$VPC_ID" --query 'RouteTables[?Associations[0].Main == `true`]' --output text | grep  rtb| awk '{print $1}')  
#EC2_ID=$(aws ec2 describe-instances --filter "Name=tag:Name,Values=$STACK_NAME-csye6225-ec2" --query 'Reservations[*].Instances[*].{id:InstanceId}' --output text)
# --query 'RouteTables[?Associations[0].Main != `true`]'

echo VPC ID IS $VPC_ID
echo Internate gateway Id is $IG_GATEWAY
echo Route Table Id is $ROUTE_TABLE_ID
#echo $ROUTE_TABLE_ID2

echo detaching gayeway

aws ec2 detach-internet-gateway --internet-gateway-id=$IG_GATEWAY --vpc-id=$VPC_ID

echo done...........

echo deleting gateway

aws ec2 delete-internet-gateway --internet-gateway-id $IG_GATEWAY

echo done...........

echo deleting route tables

aws ec2 delete-route-table --route-table-id $ROUTE_TABLE_ID

echo done...........


echo deleting vpc

aws ec2 delete-vpc --vpc-id $VPC_ID

echo done........... Bye

