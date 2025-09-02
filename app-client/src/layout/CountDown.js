import { useEffect, useState } from "react";
import { Alert } from "react-bootstrap";

const Countdown = ({ seconds, onFinish }) => {
    const [timeLeft, setTimeLeft] = useState(seconds);

    useEffect(() => {
        if (timeLeft <= 0) {
            onFinish();
            return;
        }
        const interval = setInterval(() => setTimeLeft(t => t - 1), 1000);
        return () => clearInterval(interval);
    }, [timeLeft, onFinish]);

    return <Alert variant="secondary"> Chuẩn bị: {timeLeft} giây</Alert>;
};

export default Countdown;