addEventListener("load", (evt) => {
    const canvasMain = document.getElementById("canvasMain");

    const width = canvasMain.width;
    const height = canvasMain.height;
    const ctx = canvasMain.getContext("2d");

    const circleRadius = 52;
    let angle = 0;

    class Circle {
        constructor() {
        }
        update() {
        }
        draw() {
            // Kreis
            ctx.strokeStyle = 'rgba(210,210,210,0.8)';
            ctx.beginPath();
            ctx.arc(width/2, height/2, circleRadius, 0, 2 * Math.PI);
            ctx.lineWidth = 1;
            ctx.stroke();

            // Punkt
            // ctx.fillStyle = 'rgba(0,255,0,0.4)';
            // ctx.fillRect(this.x, this.y, this.radius/2, this.radius/2);
        }
    }

    class Text {
        constructor(x, y) {
            this.x = x;
            this.y = y;
            this.message = "Winkel: ???";
        }
        update() {
            this.message = "Winkel: " + angle;
        }
        draw() {
            ctx.font = "20px Impact";
            ctx.fillStyle = "gray";
            ctx.fillText(this.message, this.x + 2, this.y + 2);
            ctx.fillStyle = "rgba(210, 210, 210, 1)";
            ctx.fillText(this.message, this.x, this.y);
        }
    }

    class Point {
        constructor() {
            this.radius = 4;
            this.x = width/2 + circleRadius;
        }
        update() {
        }
        draw() {
            const radian = angle * (pi/180);
            // Kreis
            ctx.strokeStyle = 'rgba(0,255,0,0.8)';
            ctx.fillStyle = 'rgba(0,255,0,0.8)';
            ctx.beginPath();
            ctx.arc(this.x, height/2, this.radius, 0, 2 * Math.PI);
            ctx.fill();
            ctx.lineWidth = 1;
            ctx.stroke();

            // Punkt
            // ctx.fillStyle = 'rgba(0,255,0,0.4)';
            // ctx.fillRect(this.x, this.y, this.radius/2, this.radius/2);
        }
    }

    const elements = [];
    elements.push(new Circle());
    elements.push(new Text(35, 50));
    elements.push(new Point());

    let lastTimestamp = 0;

    const updateIntervall = 250;
    let updateCounter = 0;

    function animate(timestamp) {
        const tsDifference = timestamp - lastTimestamp;
        if (updateCounter < updateIntervall) {
            updateCounter += tsDifference;
        } else {
            ctx.fillStyle = 'black';
            ctx.fillRect(0, 0, width, height);
            [...elements].forEach((element) => {
                element.update();
                element.draw();
            });
            angle += 1;
            if (360 < angle) angle = 0;
            updateCounter = 0;
        }
        // console.log(tsDifference);
        lastTimestamp = timestamp;
        requestAnimationFrame(animate);
    }
    animate(0);
    
    console.log("...loaded!");
});

