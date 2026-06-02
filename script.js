window.addEventListener("load", () => {
  const hero = document.querySelector(".hero-container");

  if (hero) {
    hero.classList.add("show");
  }

  animateElements(".fade", 150);
  animateElements(".animate-text", 150);
  animateElements(".zoom", 150);
  animateElements(".service-card", 180);
  animateElements(".project-card", 180);
  animateElements(".info-card", 180);
  animateElements(".stat-box", 180);
  animateElements(".contact-card", 180);

  addJumpEffect();
  startCounters();
});

function animateElements(selector, delay) {
  const elements = document.querySelectorAll(selector);

  elements.forEach((element, index) => {
    setTimeout(() => {
      element.classList.add("show");
    }, index * delay);
  });
}

function addJumpEffect() {
  const cards = document.querySelectorAll(
    ".service-card, .project-card, .info-card, .stat-box, .contact-card"
  );

  cards.forEach(card => {
    card.addEventListener("mouseenter", () => {
      card.style.transform = "translateY(-25px) scale(1.04)";
      card.style.boxShadow =
        "0 18px 35px rgba(37, 99, 235, 0.20), 0 0 25px rgba(37, 99, 235, 0.12)";
      card.style.backgroundColor = "#F8FBFF";
      card.style.borderColor = "#2563EB";
    });

    card.addEventListener("mouseleave", () => {
      card.style.transform = "translateY(0) scale(1)";
      card.style.boxShadow = "0 8px 18px rgba(30, 58, 138, 0.08)";
      card.style.backgroundColor = "#ffffff";
      card.style.borderColor = "#BFDBFE";
    });
  });
}

function startCounters() {
  const counters = document.querySelectorAll(".count");

  counters.forEach(counter => {
    const target = Number(counter.getAttribute("data-target"));
    let current = 0;
    const increment = target / 80;

    function updateCounter() {
      current += increment;

      if (current < target) {
        counter.innerText = Math.floor(current);
        requestAnimationFrame(updateCounter);
      } else {
        counter.innerText = target;
      }
    }

    updateCounter();
  });
}