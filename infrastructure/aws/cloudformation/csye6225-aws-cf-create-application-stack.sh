
#aws iam attach-user-policy --policy-arn arn:aws:iam::413944663154:policy/Travis-Code-Deploy --user-name testTravis

#echo 'export AWS_ACCESS_KEY_ID='$AWS_ACCESS_KEY_ID > /root/setenv


#aws ec2 describe-instances --profile testTravis

#aws iam get-user --user-name testTravis --output text
#aws iam get-user --user-name testTravis --filter Name=tag:Name,Values=ADS-prod-ads

echo "Updating Stack"
stack_name=$1

echo "Enter Secure Key name"
read idRsa

echo "Enter domain name for s3 bucket"
read dname

echo "Please enter EC2 fo tag matching"
read ec2tagmatchingname

echo "Enter your certifciate number"
read cert

echo "Enter your DNS name"
read dns

Iamprofilename=CodeDeployEC2ServiceRoleProfile

fname=web-app.$dname
tld=.me

echo $stack_name
subnetExportName1="csye6225-cloud-Networking-db-subnet1Id"
subnetExportName2="csye6225-cloud-Networking-db-subnet2Id"
stackId=$(aws cloudformation create-stack --stack-name $stack_name --template-body file://csye6225-cf-application.json --parameters ParameterKey=subnetExportName1,ParameterValue=$subnetExportName1 ParameterKey=subnetExportName2,ParameterValue=$subnetExportName2 ParameterKey=keyTag,ParameterValue=$idRsa ParameterKey=NameTag,ParameterValue=$ec2tagmatchingname ParameterKey=S3BucketTag,ParameterValue=$fname$tld ParameterKey=IamTag,ParameterValue=$Iamprofilename ParameterKey=CertificateArnNumber,ParameterValue=$cert ParameterKey=originalDomain,ParameterValue=$dns --query [StackId] --output text)

#aws ec2 associate-iam-instance-profile --instance-id i-123456789abcde123 --iam-instance-profile Name=admin-role

echo "Stack id is" 

echo $stackId

if [ -z $stackId ]; then
    echo 'Error occurred.Dont proceed. TERMINATED'
else
    aws cloudformation wait stack-create-complete --stack-name $stack_name
    echo "STACK CREATION COMPLETE."
fi
