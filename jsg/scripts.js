addEventListener("load", (evt) => {
    const canvasMain = document.getElementById("canvasMain");

    const width = canvasMain.width;
    const height = canvasMain.height;
    const ctx = canvasMain.getContext("2d");

    const circleRadius = 52;
    const circleX = width * 0.75;
    const circleY = height * 0.75;

    let angle = 0;
    let radian = 0;

    class Circle {
        constructor() {
        }
        update() {
        }
        draw() {
            // Kreis
            ctx.strokeStyle = 'rgba(210,210,210,0.8)';
            ctx.beginPath();
            ctx.arc(circleX, circleY, circleRadius, 0, 2 * Math.PI);
            ctx.lineWidth = 1;
            ctx.stroke();

            // Punkt
            // ctx.fillStyle = 'rgba(0,255,0,0.4)';
            // ctx.fillRect(this.x, this.y, this.radius/2, this.radius/2);
        }
    }

    class Point {
        constructor() {
            this.radius = 4;
            this.x = circleX;
            this.posX = this.x;
            this.y = circleY;
            this.posY = this.y;
            this.color = "rgba(80,80,80,0.6)"
        }
        update() {
        }
        draw() {
            // Kreis
            ctx.strokeStyle = this.color;
            ctx.fillStyle = this.color;
            ctx.beginPath();
            ctx.arc(this.posX, this.posY, this.radius, 0, 2 * Math.PI);
            ctx.fill();
            ctx.lineWidth = 1;
            ctx.stroke();
        }
    }

    class CirclePoint extends Point {
        constructor() {
            super();
            this.x = circleX + circleRadius;
            this.color = "rgba(0,255,0,0.8)";
        }
        update() {
            const offsetX = Math.cos(radian) * circleRadius;
            this.posX = this.x - circleRadius + offsetX;
            const offsetY = Math.sin(radian) * circleRadius;
            this.posY = this.y + offsetY;
        }
    }

    class CenterPoint extends Point {
        constructor() {
            super();
            this.x = circleX + circleRadius;
        }
    }

    class Display {
        constructor(x, y) {
            this.x = x;
            this.y = y;
            this.message = "???";
        }
        update() {
        }
        draw() {
            ctx.font = "20px Impact";
            ctx.fillStyle = "gray";
            ctx.fillText(this.message, this.x + 2, this.y + 2);
            ctx.fillStyle = "rgba(210, 210, 210, 1)";
            ctx.fillText(this.message, this.x, this.y);
        }
    }

    class DisplayAngle extends Display {
        update() {
            super.update();
            this.message = "Winkel: " + angle;
        }
    }

    class DisplayRadian extends Display {
        update() {
            super.update();
            this.message = "Radian: " + radian;
        }
    }

    class Graph {
        constructor() {
            this.length = (circleRadius * 2) * 2;
            this.x = 0;
            this.y = 0;
            this.width = 0;
            this.height = 0;
        }
        update() {
        }
        draw() {
            ctx.fillStyle = "rgba(80,80,80,0.6)";
            ctx.fillRect(this.x, this.y, this.width, this.height);
        }
    }

    // waagerecht
    class GraphHorizontal extends Graph {
        constructor() {
            super();
            this.x = circleX - circleRadius - this.length - 20;
            this.y = circleY - circleRadius;
            this.width = this.length;
            this.height = circleRadius * 2;
        }
    }

    // senkrecht
    class GraphVertical extends Graph {
        constructor() {
            super();
            this.x = circleX - circleRadius;
            this.y = circleY - circleRadius - this.length - 20;
            this.width = circleRadius * 2;
            this.height = this.length;
        }
    }

    const elements = [];
    elements.push(new Circle());
    elements.push(new DisplayAngle(20, 35));
    elements.push(new DisplayRadian(20, 70));
    elements.push(new GraphVertical());
    elements.push(new GraphHorizontal());
    elements.push(new CenterPoint());
    elements.push(new CirclePoint());

    let lastTimestamp = 0;

    const updateIntervall = 50;
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
            radian = angle * (Math.PI/180);
            // console.log("radian: " + radian);
            updateCounter = 0;
        }
        // console.log(tsDifference);
        lastTimestamp = timestamp;
        requestAnimationFrame(animate);
    }
    animate(0);
    
    console.log("...loaded!");
});

