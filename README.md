# rewfus82.github.io

Personal portfolio for **Ryan McLaughlin** — Software Engineer focused on
production AI systems: multi-agent orchestration with LangGraph, custom MCP
servers, and human-in-the-loop workflows.

**Live:** https://rewfus82.github.io

## Stack

Hand-written static site — no framework, no build step.

- `index.html` — single-page layout (hero, about, skills, projects, experience, contact)
- `css/styles.css` — custom-property dark theme, CSS grid/flex, responsive `clamp()` typography
- `js/scripts.js` — vanilla JS: sticky nav, mobile menu, scroll-spy, `IntersectionObserver` reveals

## Featured project

[**Mise-en-Place**](https://github.com/rewfus82/mise-en-place) — a multi-agent
meal-planning system (LangGraph + custom MCP servers + LangSmith, React/FastAPI).

## Develop locally

Any static server works, e.g.:

```bash
python -m http.server 8000
```

Then open http://localhost:8000.
