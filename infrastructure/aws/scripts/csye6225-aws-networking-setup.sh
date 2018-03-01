#!/bin/bash

VPC_NAME="$1"
echo "Enter Region Name you want to create AWS for"
read AWS_REGION

echo "Enter the CIDR"
read VPC_CIDR

if [[ $VPC_CIDR =~ ^^([0-9]{1,3}\.){3}[0-9]{1,3}(\/([0-9]|[1-2][0-9]|3[0-2]))$ ]]
	then
	echo "$VPC_CIDR is entered"
else
	echo "$VPC_CIDR is not valid"
	exit
fi


echo "Enter Route Destination"
read DEST

if [[ $DEST =~ ^^([0-9]{1,3}\.){3}[0-9]{1,3}(\/([0-9]|[1-2][0-9]|3[0-2]))$ ]]
	then
	echo "$DEST is noted"
else
	echo "$DEST is not correct Destination"
	exit
fi

if [[ $AWS_REGION -eq "us-east-1" ]]; then
	echo "Creating VPC in '$AWS_REGION' region..."
	VPC_ID=$(aws ec2 create-vpc --cidr-block $VPC_CIDR --query 'Vpc.{VpcId:VpcId}' --output text --region $AWS_REGION)
else
	echo "check your aws region"
fi


if [[ -z $VPC_ID ]]; then
	echo "To err is human, not AWS, check your code (CIDR and Route Destination) buddy"
	exit
else
	echo "VPC ID '$VPC_ID' CREATED in '$AWS_REGION' region."
fi


aws ec2 create-tags --resources $VPC_ID --tags "Key=Name,Value=$VPC_NAME" --output text  --region $AWS_REGION
echo "VPC ID '$VPC_ID' NAME is '$VPC_NAME'."

echo "Internet Gateway is being created..."
IGW_ID=$(aws ec2 create-internet-gateway --query 'InternetGateway.{InternetGatewayId:InternetGatewayId}' --output text --region $AWS_REGION)

if [[ -z $IGW_ID ]]; then
	echo "To err is human, not AWS, check your code (Internet Gateway)"
	exit
else
	echo "  Internet Gateway ID '$IGW_ID' CREATED."
fi


# Attach Internet gateway to your VPC
aws ec2 attach-internet-gateway --vpc-id $VPC_ID --internet-gateway-id $IGW_ID --region $AWS_REGION
echo "  Internet Gateway ID '$IGW_ID' ATTACHED to VPC ID '$VPC_ID'."

# Create Route Table
echo "Route Table is being created"
ROUTE_TABLE_ID=$(aws ec2 create-route-table --vpc-id $VPC_ID --query 'RouteTable.{RouteTableId:RouteTableId}' --output text --region $AWS_REGION)
if [[ -z $ROUTE_TABLE_ID ]]; then
	echo "To err is human, not AWS, check your code (ROUTE_TABLE)"
	exit
else
	echo "  Route Table ID '$ROUTE_TABLE_ID' CREATED."
fi


# Create route to Internet Gateway
RESULT=$(aws ec2 create-route --route-table-id $ROUTE_TABLE_ID --destination-cidr-block $DEST --gateway-id $IGW_ID --region $AWS_REGION)

if [[ -z $RESULT ]]; then
	echo "To err is human, not AWS, check your code (ROUTE_TABLE)"
	exit
else
	echo "  Route to '$DEST' via Internet Gateway ID '$IGW_ID' ADDED to" "Route Table ID '$ROUTE_TABLE_ID'."
fi

#send-task-failure --task-token --error


#output=`aws ec2 describe-stack --query "Rese.Instances[].[PublicIpAddress,Tags[?Key=='Name'].Value]" --filter Name=tag:Name,Values=${name} --output text`

#output2= $(aws ec2 describe-stacks --stack-name $VPC_Name)
#echo $output2




#if [ -n "$output" ]; then
#    echo "$output"
#else
#echo "no error found"
#fi


