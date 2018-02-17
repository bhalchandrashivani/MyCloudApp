s_name="$1"
c_const=csye6225
vpc_const=vpc
ig_const=InternetGateway
route_table_const=route-table
vpcTag=$s_name$c_const$vpc_const

EC2_ID=$(aws ec2 describe-instances --filter "Name=tag:Name,Values=$s_name" --query 'Reservations[*].Instances[*].{id:InstanceId}' --output text)

echo $EC2_ID

aws ec2 modify-instance-attribute --instance-id $EC2_ID --no-disable-api-termination

aws cloudformation delete-stack --stack-name $s_name
aws cloudformation wait stack-delete-complete --stack-name $s_name
echo "STACK Deleted"