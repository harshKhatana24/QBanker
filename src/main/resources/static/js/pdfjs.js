function generatePDF(e) {
    // e.preventDefault()
    const noOfSets = parseInt(document.getElementById('noOfSets').value) || 1;
    const content = document.getElementById('content').innerHTML;
    const currentDate = new Date().toLocaleDateString();
    const instituteLogo = "https://iitram.ac.in/upload/iitram_logo_only.jpg";
    // const instructions = document.querySelector(".instructions");
    const time  = document.getElementById('Time').value;
    const coursecode = document.getElementById('Coursecode').value;
    const totalmark = document.getElementById('Marks').value;
    const coursename =document.getElementById('Coursename').value ;
    const date  = document.getElementById('Date').value;
    const programmesemexam = document.getElementById('Prosemexam').value;

    console.log(time, coursecode,coursename,date,totalmark,programmesemexam)
    // Fetch header content from another endpoint
    fetch('/api/pdf/header/get', { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch header.');
            }
            return response.text();
        })
        .then(headerContent => {
            // Combine header + main content, inserting the date dynamically
            const fullContent = `
                <html>
                <head>
                    <style>
                        @page { size: A4; margin: 20mm 15mm; }
                        @page { 
                             @bottom-center { content: "Page " counter(page) " of " counter(pages); font-size: 12px; }
                        }
                        body { font-family: Arial, sans-serif; line-height: 1; text-align: justify; }

                        .header-container {
                           height:100px ;
                            margin-bottom: 10px;
                            /*display: flex;*/
                            /*justify-content: space-around;*/
                            /*align-items: center;*/
                            
                            }
                        .header-container img {
                            width: 75px;
                            height: auto;
                        }
                        .institute-name {
                            font-size: 18px;
                            font-weight: bold;
                            margin-top: -60px;
                            
                            margin-left: 85px ;
                        }
                        .institute-location{
                            font-size: 14px;
                            font-weight: bold;
                            margin-top: 5px;
                            
                            margin-left: 250px ;
                        }
                        .date {
                            font-size: 15px;
                            margin-top: 5px;
                            text-align: left;
                            font-weight: bolder;
                        }
                        /* 🖼️ Image Container Styling */
                        .image-space {
                            display: flex;
                            justify-content: center;
                            align-items: center;
                           page-break-inside: avoid;
                            border: 1px solid #ddd; /* Optional: Adds a light border */
                            margin: 10px auto; /* Centering */
                           
                        }

                        .image-space img {
                            display: block;
                            height: auto;

                            width:100%;
                            object-fit: cover;
                            margin-bottom: 20px;
                            
                        }
                        .sem-and-term{
                            text-align: center;
                            font-size: 18px;
                            font-weight: bolder;
                            margin-top: -25px;
                            
                        }
                        .total-mark{
                            font-size:15px;
                            margin-top: -10px;
                            text-align: right;
                            font-weight: bolder;
                        }
                        .subject-name{
                        text-align: center;
                            font-size: 20px;
                            font-weight: bolder;
                            margin-top: 5px;
                        }
                        .time{
                        text-align: right;
                            font-size: 15px;
                            font-weight: bolder;
                            margin-top: -10px;
                        }
                        .course-code{
                        text-align: left;
                            font-size: 18px;
                            font-weight: bolder;
                            margin-top: 10px;
                        }
                        .instructions{
                            text-align: left;
                            font-size: 15px;
                            font-weight: bold;
                            margin-top: 5px;
                        }
                        .separator{
                        height: 1px;
                        color: black;
                        margin-top: 5px;
                        width: 100%;
                        background-color: black;
                        }
                    </style>
                </head>
                <body>
                    <div class="header-container">
                        <img src="${instituteLogo}" alt="Institute Logo" onerror="this.style.display='none'">
                        <div class="institute-name">Institute of Infrastructure Technology Research And Management</div>
                        <div class ="institute-location">Ahmedabad - 380026 , Gujarat </div>
                        
                        
                       
                    </div>
                    
                    <div>
                    <div class="sem-and-term"> ${programmesemexam}</div>
                    <div class="date">Date:  ${date}</div>
                    <div class="total-mark">Total Marks : ${totalmark}</div>
                    <div class="course-code">Course Code : ${coursecode}</div>
                    <div class="time">Time : ${time}</div>
                    <div class="subject-name"> ${coursename}</div>
                    <div class="separator "></div>
                    <div class="instructions">
                        <strong>Instructions:</strong>
                        <p>(1)  Read all the questions carefully. Answer each question in the space provided.</p>
                        <p>(2)  No form of plagiarism will be tolerated.</p>
                        <p>(3)  Cheating during the exam will lead to case of UFM</p>
                    </div>
                     <div class="separator "></div>
                      </div>
<!--                    <div id="header">${headerContent}</div>-->
                    <div id="content">${content}</div>
                </body>
                </html>`;

            // Send combined HTML to the backend for PDF generation
            return fetch('/api/pdf/generate', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ noOfSets: noOfSets, htmlContent: fullContent })
            });
        })
        .then(response => {
            if (response.ok) {
                return response.blob();
            }
            throw new Error('Failed to generate PDF.');
        })
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = noOfSets > 1 ? 'QuestionBanks.zip' : 'QuestionBank.pdf';
            document.body.appendChild(a);
            a.click();
            a.remove();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to generate PDF: ' + error.message);
        });
}
