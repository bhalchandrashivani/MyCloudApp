
#aws iam attach-user-policy --policy-arn arn:aws:iam::413944663154:policy/Travis-Code-Deploy --user-name testTravis

#echo 'export AWS_ACCESS_KEY_ID='$AWS_ACCESS_KEY_ID > /root/setenv


#aws ec2 describe-instances --profile testTravis

aws iam get-user --user-name testTravis --output text
#aws iam get-user --user-name testTravis --filter Name=tag:Name,Values=ADS-prod-ads


aws ec2 associate-iam-instance-profile --instance-id i-123456789abcde123 --iam-instance-profile Name=admin-role


