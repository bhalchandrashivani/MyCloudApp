aws ec2 run-instances   --image-id ami-66506c1c --key-name danish9412 --security-groups EC2SecurityGroup --instance-type t2.micro --placement AvailabilityZone=us-east-1 --block-device-mappings DeviceName=/dev/sdh,Ebs={VolumeSize=100} --count 1