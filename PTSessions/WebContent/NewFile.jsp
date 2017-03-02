<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
	<!--[if IE]><link rel="shortcut icon" href="images/favicon.png"><![endif]-->
	<link rel="icon" href="images/favicon.png">
	<link rel="apple-touch-icon" href="images/favicon.png">
		<title><% 
		String str = (String) request.getAttribute("Name");
		if(str != null){
			str.replaceAll("/"," ");
		}
		out.println(str); 
		%>
		</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="assets/css/main.css" />
		<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
	</head>
	<body>
		<div id="page-wrapper">

			<!-- Header -->
				<header id="header">
					<h1>
					<%
					out.println(str);
					%>
					</h1>
					<nav id="nav">
						<ul>
							<li><a href="index.html">Home</a></li>
							<li>
								<a href="#" class="icon fa-angle-down">Menu</a>
								<ul>
									<li><a href="PTServlet?MN=2">ProTools</a></li>
									<li><a href="Videos.jsp">Videos</a></li>
									<li><a href="Images.jsp">Photos</a></li>
									<li><a href="Complete.jsp">Audio</a></li>
									<li><a href="https://github.com/FaustAndLaLuna">Code</a></li>
								</ul>
							</li>
						</ul>
					</nav>
				</header>
				
			<!-- Main -->
				<section id="main" class="container">
					<header>
						<h2><% out.println(str); %></h2>
						<p>ProTools Session Folder <br />Size: <% out.println((String) request.getAttribute("Size")); %>
						<br />Session number: <% 
						String id = (String) request.getAttribute("ID");
						out.println(id); %> </p>
					</header>
					<div class="box">
						<!--  <span class="image featured"><img src="images/pic01.jpg" alt="" /></span> -->
						<h3>Comments:</h3>
						<p><% out.println((String) request.getAttribute(("Comments"))); %></p>
						<hr>
						<h3>Images</h3> <br/>
						<% out.println((String) request.getAttribute(("Images"))); %>
						<hr>
						<h3>Videos</h3> <br/>
						<% out.println((String) request.getAttribute(("Videos"))); %>
						<hr>
						<h3>Download</h3><br />
						<a href="/PTServlet?ID=<% out.println(id); %>&MN=3" class="button">Download</a>
					</div>
				</section>

			<!-- Footer -->
				<footer id="footer">
					<ul class="icons">
						<li><a href="https://twitter.com/NikkoAndFaust" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
						<li><a href="https://www.instagram.com/FaustAndLaLuna/" class="icon fa-instagram"><span class="label">Instagram</span></a></li>
						<li><a href="https://github.com/FaustAndLaLuna" class="icon fa-github"><span class="label">Github</span></a></li>
						<li><a href="https://www.youtube.com/dovahart" class="icon fa-youtube"><span class="label">Dribbble</span></a></li>
						<li><a href="https://soundcloud.com/faustandlaluna" class="icon fa-soundcloud"><span class="label">Google+</span></a></li>
					</ul>
					<ul class="copyright">
						<li>&copy; FaustAndLaLuna. All rights reserved.</li><li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
					</ul>
				</footer>

		</div>

		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/jquery.dropotron.min.js"></script>
			<script src="assets/js/jquery.scrollgress.min.js"></script>
			<script src="assets/js/skel.min.js"></script>
			<script src="assets/js/util.js"></script>
			<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
			<script src="assets/js/main.js"></script>

	</body>
</html>
