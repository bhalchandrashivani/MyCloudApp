s_name=$1
c_const=csye6225
vpc_const=vpc
ig_const=InternetGateway
route_table_const=route-table
vpcTag=$s_name$c_const$vpc_const

echo "Enter EC2 Tag name to disable API termination"
read api

EC2_ID=$(aws ec2 describe-instances --filter "Name=tag:Name,Values=$api" --query 'Reservations[*].Instances[*].{id:InstanceId}' --output text)

echo "Identifying EC2..."

echo $EC2_ID

echo "disabling API termination............"

aws ec2 modify-instance-attribute --instance-id $EC2_ID --no-disable-api-termination

echo "stack name $s_name is being deleted........."

echo "please wait for confirmation, This may take some time"

aws cloudformation delete-stack --stack-name $s_name
aws cloudformation wait stack-delete-complete --stack-name $s_name
echo "STACK Deleted"



