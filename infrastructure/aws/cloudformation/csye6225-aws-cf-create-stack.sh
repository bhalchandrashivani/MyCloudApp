sName=$1

c_const=csye6225
vpc_const=vpc
internetgateway_const=InternetGateway
route_const=route-table

vpcTag=$sName$c_const$vpc_const

echo $vpcTag

stackID=$(aws cloudformation create-stack --stack-name $sName --template-body file://csye6225-cf-networking.json --parameters ParameterKey=vpcTag,ParameterValue=$vpcTag ParameterKey=internetgatewayTag,ParameterValue=sName$c_const$internetgateway ParameterKey=routeTag,ParameterValue=$sName$c_const$route_const --query [StackId] --output text)

echo $stackID

if [ -z $stackID ]; then

	echo 'Error... exiting'

else

	aws cloudformation wait stack-create-complete --stack-name $stackID
	echo "Strack Creation Done"
	#statements
fi
