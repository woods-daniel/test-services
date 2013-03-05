<% 
	String url = (String)session.getAttribute("companyLogoUrl");
	if (url == null) {
		url = "/default/images/dummy-co.png";
	}
 %>

<style type="text/css">
#noesis-home-logo{
display: inline-block;
background-image: url(http://noesisimg.s3.amazonaws.com/yellow-fin-images/NoesisLogo-wht-txt.png);
background-position:center center;
background-repeat: no-repeat;
width: 200px;
height: 47px;
padding: 20px ;
}

#company-logo{
display: inline-block;
background-image: url(<%= url %>);
background-position:center center;
background-repeat: no-repeat;
width: 200px;
height: 47px;
padding:20px;
float:right;
}



#noesis-home-logo-box{
width: 100%;
background: #444;
height:auto;
display:block;
}
 @media (-webkit-min-device-pixel-ratio: 2), (min-resolution: 192dpi) {
 #noesis-home-logo{
background-image: url(http://noesisimg.s3.amazonaws.com/yellow-fin-images/NoesisLogo-wht-txt.svg);
-webkit-background-size:contain;
-moz-background-size:contain;
background-size:contain;
width: 200px;
height: 50px;
}
}
</style>

<div id="noesis-home-logo-box"><div id="noesis-home-logo"></div><div id="company-logo"/></div></div>
