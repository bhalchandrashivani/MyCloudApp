sName=$1

c_const=csye6225
private=private
public=public
vpc_const=vpc
internetgateway_const=InternetGateway
route_const=route-table
dash=-
webtag=webapp
dbtag=rds

vpcTag=$sName$c_const$vpc_const

echo $vpcTag

stackID=$(aws cloudformation create-stack --stack-name $sName --template-body file://csye6225-cf-networking.json --parameters ParameterKey=vpcTag,ParameterValue=$vpcTag ParameterKey=internetgatewayTag,ParameterValue=sName$c_const$internetgateway ParameterKey=routeTag,ParameterValue=$sName$dash$c_const$dash$public$dash$route_const ParameterKey=PrivaterouteTag,ParameterValue=$sName$dash$c_const$dash$private$dash$route_const ParameterKey=SecurityGroupWebTag,ParameterValue=$c_const$dash$webtag ParameterKey=SecurityGroupdbTag,ParameterValue=$c_const$dash$dbtag --query [StackId] --output text)

echo $stackID

if [ -z $stackID ]; then

	echo 'Error... exiting'

else

	aws cloudformation wait stack-create-complete --stack-name $stackID
	echo "Strack Creation Done"
	#statements
fi
