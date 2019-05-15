$(() => {
	// Actions à effectuer au chargement de la page
	initPages();

	$('#pages').children().detach();
	ajax('whoami', '', (data) => {
		if (data.utilisateur) {
			utilisateur = data.utilisateur;
			premierChargementConnecte(findPageByPath(window.location.pathname));
		} else {
			pageTab.pageConnexion.changePage();
		}
	}, () => {
		pageTab.pageConnexion.changePage();
	});

	window.onpopstate = (ev) => {
		if (utilisateur.length === 0) {
			pageTab.pageConnexion.changePage();
		} else {
			findPageByPath(window.location.pathname).changePage();
		}
	};

	// Toggle de la sidebar
	$("#menu-toggle").click(function (e) {
		e.preventDefault();
		$("#wrapper").toggleClass("toggled");
	});
});