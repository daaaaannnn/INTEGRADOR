import { api } from './api.js';

const form = document.querySelector('#loginForm');
const msg = document.querySelector('#loginMessage');

function showMessage(text, type = 'info') {
  msg.textContent = text;
  msg.className = `message ${type === 'error' ? 'error' : ''}`;
}

form.addEventListener('submit', async (event) => {
  event.preventDefault();
  showMessage('Validando credenciales...');
  const correo = document.querySelector('#correo').value.trim();
  const password = document.querySelector('#password').value;
  try {
    const user = await api.login(correo, password);
    localStorage.setItem('GPA_SESSION', JSON.stringify(user));
    location.href = 'app.html';
  } catch (error) {
    showMessage(error.message || 'No fue posible iniciar sesión.', 'error');
  }
});
