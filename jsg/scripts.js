addEventListener("load", (evt) => {
    const canvasMain = document.getElementById("canvasMain");

    const COLOR_GRAY = "rgba(210,210,210,0.8)";
    const COLOR_GREEN = "rgba(0,255,0,0.8)";
    const COLOR_BLUE = "rgba(0,0,255,0.8)";

    const width = canvasMain.width;
    const height = canvasMain.height;
    const ctx = canvasMain.getContext("2d");

    const circleRadius = 52;
    const circleX = width * 0.15;
    const circleY = height * 0.75;

    class Angle {
        constructor(offset) {
            this.degree = 0 + offset;
            this.radian = 0;
            this.sin_of_radian = 0;
            this.cos_of_radian = 0;
        }
        update() {
            // plus 1 rechnen
            this.degree += 1;
            if (this.degree > 360) this.degree = 0;
            // abhängige Werte
            // angleShow = (angle > 90 ? (angle - 90) : (360 - 90 + angle));
            this.radian = this.degree * (Math.PI / 180);
            this.sin_of_radian = Math.cos(this.radian);
            this.cos_of_radian = Math.sin(this.radian);
        }
    }
    const angleDraw = new Angle(0);
    const angleShow = new Angle(90);

    class Point {
        constructor(x, y) {
            this.x = x;
            this.y = y;
        }
    }

    const circlePoint = new Point(0, 0);

    class Stage {
        constructor(ctx) {
            this.ctx = ctx;
        }
        drawCircle(x, y, degree, color) {
            this.ctx.strokeStyle = color;
            this.ctx.lineWidth = 1;
            this.ctx.beginPath();
            this.ctx.arc(x, y, circleRadius, 0, degree);
            this.ctx.stroke();
            this.ctx.lineWidth = 1;

        }
        drawPoint(x, y, color) {
            this.ctx.strokeStyle = color;
            this.ctx.fillStyle = color;
            this.ctx.beginPath();
            this.ctx.arc(x, y, 4, 0, 2 * Math.PI);
            this.ctx.fill();
            this.ctx.lineWidth = 1;
            this.ctx.stroke();
        }
        drawLine(x1, y1, x2, y2) {
            this.ctx.strokeStyle = COLOR_GRAY;
            this.ctx.beginPath();
            this.ctx.moveTo(x1, y1);
            this.ctx.lineTo(x2, y2);
            this.ctx.stroke(); 
        }
    }

    class Circle {
        constructor(stage) {
            this.stage = stage;
            this.x = circleX;
            this.y = circleY;
            this.pointX = circleX + circleRadius;
            this.pointY = circleY;
        }
        update() {
            // X
            const offsetX = angleDraw.cos_of_radian * circleRadius * -1;
            this.pointX = this.x + offsetX;
            // Y
            const offsetY = angleDraw.sin_of_radian * circleRadius * -1;
            this.pointY = this.y + offsetY;
        }
        draw() {
            // Mittelpunkt
            this.stage.drawPoint(this.x, this.y, "rgba(80,80,80,0.6)");

            // Kreis
            this.stage.drawCircle(this.x, this.y, 2 * Math.PI, COLOR_GREEN);
            
            const progress = ((2 * Math.PI / 180) - angleShow.radian);
            this.stage.drawCircle(this.x, this.y, progress, "rgba(0,0,0,1)");

            // Kreispunkt
            this.stage.drawPoint(this.pointX, this.pointY, COLOR_GREEN);

        }
    }

    class Display {
        constructor(lineNr, content) {
            this.x = circleX + circleRadius + 20;
            this.y = 15 + (lineNr * 30);
            this.content = content;
            this.message = "???";
        }
        update() {
            if (this.content === "angle") {
                this.message = "Winkel: " + angleShow.degree; // + "° | " + angleDraw.degree + "° | " + (angleDraw.degree - angleShow.degree + "°");
            } else if (this.content === "radian") {
                this.message = "Radian: " + angleShow.radian;
            } else if (this.content === "sin_of_radian") {
                this.message = "sin: " + angleShow.sin_of_radian;
            } else if (this.content === "cos_of_radian") {
                this.message = "cos: " + angleShow.cos_of_radian;
            } else {
                this.message = "???";
            }
        }
        draw() {
            ctx.font = "20px Impact";
            ctx.fillStyle = "gray";
            ctx.fillText(this.message, this.x + 2, this.y + 2);
            ctx.fillStyle = "rgba(210, 210, 210, 1)";
            ctx.fillText(this.message, this.x, this.y);
        }
    }

    class Graph {
        constructor() {
            this.length = (circleRadius * 2) * 2;
            // "grüne Linie"
            this.greenLine = [];
        }
        update() {
        }
        draw() {
            // Hintergrund
            ctx.fillStyle = "rgba(80,80,80,0.6)";
            ctx.fillRect(this.x, this.y, this.width, this.height);
            // "grüner Graph"
            if (0 < this.greenLine.length) {
                ctx.beginPath();
                ctx.moveTo(this.greenLine[0].x, this.greenLine[0].y);
                this.greenLine.forEach((p) => {
                    ctx.lineTo(p.x, p.y);
                });
                ctx.stroke();
            }
        }
        storePoint(p) {
            if (this.greenLine.length < 360
                    && angleDraw.degree > 0) {
                this.greenLine.push(new Point(p.x, p.y));
            }
        }
    }

    // waagerecht
    class GraphHorizontal extends Graph {
        constructor(stage) {
            super();
            this.stage = stage;
            this.x = circleX + circleRadius + 20;
            this.y = circleY - circleRadius;
            this.width = this.length;
            this.height = circleRadius * 2;
            // Y waagerechte Achse
            this.axisY = this.y + (this.height / 2);
            // "blauer Punkt" auf Achse
            this.bluePoint = new Point(this.x, this.y + (this.height / 2));
            // "grüner Punkt" auf Graph
            this.greenPoint = new Point(this.x, this.y + (this.height / 2));
        }
        update() {
            const x = this.x + (this.length * angleShow.degree / 360);
            this.bluePoint.x = x;
            this.greenPoint.x = x;
            const offsetY = angleDraw.sin_of_radian * circleRadius * -1;
            this.greenPoint.y = this.bluePoint.y + offsetY;
            super.storePoint(this.greenPoint);
        }
        draw() {
            super.draw();
            this.stage.drawLine(this.x, this.axisY, this.x + this.width, this.axisY);
            this.stage.drawPoint(this.bluePoint.x, this.bluePoint.y, COLOR_BLUE);
            this.stage.drawPoint(this.greenPoint.x, this.greenPoint.y, COLOR_GREEN);
        }
    }

    // senkrecht
    class GraphVertical extends Graph {
        constructor(stage) {
            super();
            this.stage = stage;
            this.x = circleX - circleRadius;
            this.y = circleY - circleRadius - this.length - 20;
            this.width = circleRadius * 2;
            this.height = this.length;
            // X senkrechte Achse
            this.axisX = this.x + (this.width / 2);
            // "blauer Punkt" auf Achse
            this.bluePoint = new Point(this.x + (this.width / 2), this.y);
            // "grüner Punkt" auf Graph
            this.greenPoint = new Point(this.x + (this.width / 2), this.y + this.height);
        }
        update() {
            const y = this.y + this.height - (this.height * angleShow.degree / 360);
            this.bluePoint.y = y;
            this.greenPoint.y = y;
            const offsetX = angleDraw.cos_of_radian * circleRadius * -1;
            this.greenPoint.x = this.bluePoint.x + offsetX;
            super.storePoint(this.greenPoint);
        }
        draw() {
            super.draw();
            this.stage.drawLine(this.axisX, this.y, this.axisX, this.y + this.length);
            this.stage.drawPoint(this.bluePoint.x, this.bluePoint.y, COLOR_BLUE);
            this.stage.drawPoint(this.greenPoint.x, this.greenPoint.y, COLOR_GREEN);
        }
    }

    const stage = new Stage(ctx);

    const elements = [];
    elements.push(new Circle(stage));
    elements.push(new Display(1, "angle"));
    elements.push(new Display(2, "radian"));
    elements.push(new Display(3, "sin_of_radian"));
    elements.push(new Display(4, "cos_of_radian"));
    elements.push(new GraphVertical(stage));
    elements.push(new GraphHorizontal(stage));

    let lastTimestamp = 0;

    const updateIntervall = 10;
    let updateCounter = 0;

    function animate(timestamp) {
        const deltaTime = timestamp - lastTimestamp;
        if (updateCounter < updateIntervall) {
            updateCounter += deltaTime;

        } else {
            ctx.clearRect(0, 0, width, height);
            [...elements].forEach((element) => {
                element.update();
                element.draw();
            });

            angleDraw.update();
            angleShow.update();

            updateCounter = 0;
        }
        // console.log(deltaTime);
        lastTimestamp = timestamp;
        requestAnimationFrame(animate);
    }
    animate(0);
    
    console.log("...loaded!");
});

