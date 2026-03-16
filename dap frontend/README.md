# Defence Aspirant Portal - Frontend

React + Vite + TailwindCSS app for defence aspirants.

## Quick Start

```bash
npm install
npm run dev
```

## Vercel Deployment ✅ Ready

1. Push code to GitHub repo.
2. Connect repo to [Vercel](https://vercel.com).
3. Vercel auto-deploys on push (uses \`npm run build\`).
4. **Note:** Backend API expected at same domain /api/* (via vercel.json rewrite) or update VITE_API_BASE env var.

## Build & Preview

```bash
npm run build    # Creates dist/ folder
npm run preview  # Serves production build locally
```

## Project Structure

- \`src/pages/\`: Page components (React Router)
- \`src/services/\`: API clients
- \`src/contexts/\`: Auth context
- Admin dashboard at /admin/*

## Tech Stack

- React 19 + React Router 7
- Vite 7 + TailwindCSS 4
- Axios for API calls
- Framer Motion for animations

# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Babel](https://babeljs.io/) (or [oxc](https://oxc.rs) when used in [rolldown-vite](https://vite.dev/guide/rolldown)) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [\`typescript-eslint\`](https://typescript-eslint.io) in your project.
