import React, { useEffect, useRef, useState } from "react";
import { Alert, Button, Container, Form } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import useTextToSpeech from "../../hooks/useTextToSpeech";

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

const SpeakingTask = ({ lessonId, lessonType }) => {
    const [introduction, setIntroduction] = useState("");
    const [questions, setQuestions] = useState([]);

    useEffect(() => {
        const loadSpeakingLesson = async () => {
            try {
                const res = await Apis.get(endpoints["speaking-lesson-detail"](lessonId));
                setIntroduction(res.data.introduction);
                setQuestions(res.data.questions || []);
            } catch (err) {
                console.error(err);
            }
        };

        loadSpeakingLesson();
    }, [lessonId]);

    const [step, setStep] = useState("intro"); // intro, ready, asking, cuecard, speaking-part2, finished
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [recording, setRecording] = useState(false);
    const [audioUrl, setAudioUrl] = useState(null);

    const { voices, selectedVoiceIndex, setSelectedVoiceIndex, speak } = useTextToSpeech();
    const mediaRecorderRef = useRef(null);
    const audioChunks = useRef([]);

    useEffect(() => {
        if (voices.length > 0 && step === "intro") {
            window.speechSynthesis.cancel();
            const selectedVoice = voices[selectedVoiceIndex];

            const timer = setTimeout(() => {
                const utterance = new SpeechSynthesisUtterance(introduction);
                utterance.voice = selectedVoice;
                utterance.onend = () => setStep("ready");

                window.speechSynthesis.speak(utterance);
            }, 1000);

            return () => clearTimeout(timer);
        }
    }, [voices, selectedVoiceIndex, introduction, step]);

    const startRecording = async () => {
        const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
        mediaRecorderRef.current = new MediaRecorder(stream);

        mediaRecorderRef.current.ondataavailable = (event) => {
            if (event.data.size > 0) {
                audioChunks.current.push(event.data);
            }
        };

        mediaRecorderRef.current.onstop = () => {
            const blob = new Blob(audioChunks.current, { type: "audio/wav" });
            setAudioUrl(URL.createObjectURL(blob));
            audioChunks.current = [];
        };

        mediaRecorderRef.current.start();
        setRecording(true);
    };

    const pauseRecording = () => {
        if (mediaRecorderRef.current && mediaRecorderRef.current.state === "recording") {
            mediaRecorderRef.current.pause();
        }
    };

    const resumeRecording = () => {
        if (mediaRecorderRef.current && mediaRecorderRef.current.state === "paused") {
            mediaRecorderRef.current.resume();
        }
    }

    const stopRecording = () => {
        if (!mediaRecorderRef.current) return;
        mediaRecorderRef.current.stop();
        setRecording(false);
    };

    const startSpeakingTest = () => {
        if (lessonType === "Speaking Part 1" || lessonType === "Speaking Part 3") {
            if (questions.length === 0) return;
            const firstQuestion = questions[0];
            setCurrentQuestionIndex(0);
            setStep("asking");
            speak(firstQuestion, () => startRecording());
        } else if (lessonType === "Speaking Part 2") {
            setStep("cuecard");
        } else {
            alert("Loại bài không hợp lệ.");
        }
    };

    const nextQuestion = () => {
        pauseRecording();
        const nextIndex = currentQuestionIndex + 1;
        if (nextIndex >= questions.length) {
            setStep("finished");
            stopRecording();
            return;
        }
        const nextQuestion = questions[nextIndex];
        setCurrentQuestionIndex(nextIndex);
        speak(nextQuestion, () => resumeRecording());
    };

    return (
        <Container className="mt-4">
            <h4>IELTS Speaking Task</h4>

            <Form.Group className="mb-3">
                <Form.Label>Giọng đọc:</Form.Label>
                <Form.Select
                    value={selectedVoiceIndex}
                    onChange={(e) => {
                        const index = parseInt(e.target.value);
                        setSelectedVoiceIndex(index);
                        setStep("intro");
                    }}>
                    {voices.map((v, i) => (
                        <option key={i} value={i}>{v.name} ({v.lang})</option>
                    ))}
                </Form.Select>
            </Form.Group>

            {step === "intro" && <Alert variant="primary"><em>🔊 Đang đọc phần giới thiệu...</em></Alert>}

            {step === "ready" && (
                <Button variant="primary" onClick={startSpeakingTest}>Tôi đã sẵn sàng</Button>
            )}

            {step === "cuecard" && (
                <>
                    <Alert variant="secondary"><strong>Cue Card:</strong> {introduction}</Alert>
                    <Countdown seconds={60} onFinish={() => {
                        setStep("speaking-part2");
                        startRecording();
                    }} />
                </>
            )}

            {step === "speaking-part2" && (
                <>
                    <Alert variant="info">⏺ Bạn đang ghi âm phần trình bày Part 2.</Alert>
                    <Button variant="warning" onClick={() => {
                        stopRecording();
                        setStep("finished");
                    }}>
                        Xong câu trả lời
                    </Button>
                </>
            )}

            {step === "asking" && currentQuestionIndex >= 0 && (
                <>
                    <p><strong>Câu hỏi {currentQuestionIndex + 1}:</strong> {questions[currentQuestionIndex]}</p>
                    {recording && <Alert variant="info">⏺ Đang ghi âm... Bạn có thể bắt đầu trả lời.</Alert>}
                    <Button variant="warning" onClick={nextQuestion} disabled={!recording}>Xong câu trả lời</Button>
                </>
            )}

            {step === "finished" && (
                <div className="mt-3">
                    <p><strong>✅ Đã hoàn tất phần nói</strong></p>
                    {audioUrl && <audio controls src={audioUrl}></audio>}
                </div>
            )}
        </Container>
    );
};
export default SpeakingTask;
