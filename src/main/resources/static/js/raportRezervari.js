document.addEventListener('DOMContentLoaded', function() {
    //rezervari
    const btn = document.getElementById('afiseazaRezervariBtn');
    const lunaSelect = document.getElementById('luna');
    const anInput = document.getElementById('an');
    const container = document.getElementById('tabelaRezervariContainer');

    //clienti
    const afiseazaClientiBtn = document.getElementById('afiseazaClientiBtn');
    const dataClientInput = document.getElementById('dataClientInput');
    const clientiContainer = document.getElementById('tabelaClientiContainer');

    function fetchAndRenderReservations() {
        const luna = lunaSelect.value;
        const an = anInput.value;

        container.innerHTML = '<p>Se încarcă rezervările...</p>';
        btn.disabled = true;

        const apiUrl = `/camere/raport_lunar?an=${an}&luna=${luna}`;

        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Eroare HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then(rezervari => {
                if (rezervari.length === 0) {
                    container.innerHTML = `<p class="info-message">Nu au fost găsite rezervări pentru ${luna}/${an}.</p>`;
                } else {
                    container.innerHTML = generateReservationsTable(rezervari, luna, an);
                }
            })
            .catch(error => {
                console.error('Eroare AJAX la raportare:', error);
                container.innerHTML = '<p style="color: red;">Eroare la obținerea raportului. Verificați log-urile.</p>';
            })
            .finally(() => {
                btn.disabled = false;
            });
    }

    // Funcția care construiește HTML-ul tabelei din datele JSON
    function generateReservationsTable(rezervari, luna, an) {
        let html = `<h3>Rezervări pentru Luna ${luna} / Anul ${an}</h3>`;
        html += '<table class="data-table"><thead><tr><th>Camera</th><th>Client</th><th>Check-in</th><th>Check-out</th><th>Status</th></tr></thead><tbody>';

        rezervari.forEach(r => {
            const clientName = `${r.client.nume} ${r.client.prenume}`;
            const cameraNr = r.camera.numarCamera;

            html += `<tr>
                        <td>${cameraNr}</td>
                        <td>${clientName}</td>
                        <td>${r.dataCheckin}</td>
                        <td>${r.dataCheckout}</td>
                        <td>${r.status}</td>
                    </tr>`;
        });

        html += '</tbody></table>';
        return html;
    }

    //logica afisare clienti
    function generateClientiTable(clienti, data) {
        let html = `<h3 style="margin-top: 10px;">Clienți cazați la data de ${data}</h3>`;
        html += '<table class="data-table"><thead><tr><th>Nume Client</th><th>CNP</th><th>Serie Buletin</th><th>Adresă</th></tr></thead><tbody>';

        clienti.forEach(c => {
            html += `<tr>
                        <td>${c.nume} ${c.prenume}</td>
                        <td>${c.cnp}</td>
                        <td>${c.serieBuletin}</td>
                        <td>${c.adresa}</td>
                     </tr>`;
        });
        html += '</tbody></table>';
        return html;
    }

    function fetchAndRenderClienti() {
        const dataSelectata = dataClientInput.value;

        if (!dataSelectata) {
            clientiContainer.innerHTML = '<p style="color: red;">Vă rugăm selectați o dată.</p>';
            return;
        }

        clientiContainer.innerHTML = '<p>Se încarcă lista clienților...</p>';
        afiseazaClientiBtn.disabled = true;

        const apiUrl = `/camere/clienti_la_data?data=${dataSelectata}`;

        fetch(apiUrl)
            .then(response => response.json())
            .then(clienti => {
                if (clienti.length === 0) {
                    clientiContainer.innerHTML = `<p class="info-message">Nu au fost găsiți clienți cazați la data de ${dataSelectata}.</p>`;
                } else {
                    clientiContainer.innerHTML = generateClientiTable(clienti, dataSelectata);
                }
            })
            .catch(error => {
                console.error('Eroare AJAX la raportul de clienți:', error);
                clientiContainer.innerHTML = '<p style="color: red;">Eroare la obținerea listei de clienți. Verificați log-urile.</p>';
            })
            .finally(() => {
                afiseazaClientiBtn.disabled = false;
            });
    }

    btn.addEventListener('click', fetchAndRenderReservations);

    fetchAndRenderReservations();

    if (afiseazaClientiBtn) {
        afiseazaClientiBtn.addEventListener('click', fetchAndRenderClienti);
    }
});
