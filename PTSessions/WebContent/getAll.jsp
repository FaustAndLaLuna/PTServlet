<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
	<!--[if IE]><link rel="shortcut icon" href="images/favicon.png"><![endif]-->
	<link rel="icon" href="images/favicon.png">
	<link rel="apple-touch-icon" href="images/favicon.png">
		<title>Sessions</title>
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
					<h1><a href="index.html">All sessions</a> by Luis Edgar</h1>
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
						<h2>All Sessions</h2>
						<p>As recorded or mixed, in part or completely, by Luis Edgar.</p>
					</header>
					<div class="box" >
						<span class="image featured"><img src="images/Wow.jpeg" alt="" /></span>
						<h3>About these:</h3>
						<p>All of these sessions have been recorded and/or mixed by me. I take high pride in being able to 
						have them listed as my work. Every session has been passionately and lovingly treated as an unique
						piece of art, and they are shared, carefully, with the thought of keeping them alive.</p>
					</div>
				</section>
<section class="box">
									<div class="table-wrapper">
										<table>
											<thead>
												<tr>
													<th>ID</th>
													<th>Name</th>
													<th>Comments</th>
													<th>Folder Size</th>
													<th>Details</th>
												</tr>
											</thead>
											<tbody>
													<% out.println(request.getAttribute("allNames")); %>
											</tbody>
										</table>
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