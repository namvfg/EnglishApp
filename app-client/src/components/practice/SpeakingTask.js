// SpeakingTask.js
import React, { useEffect, useRef, useState } from "react";
import { Alert, Button, Container, Form } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import useTextToSpeech from "../../hooks/useTextToSpeech";
import Countdown from "../../layout/CountDown";

const SpeakingTask = ({ lessonId, lessonType }) => {
    const [loading, setLoading] = useState(false);

    const [introduction, setIntroduction] = useState("");
    const [questions, setQuestions] = useState([]);

    const [step, setStep] = useState("intro");
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [recording, setRecording] = useState(false);
    const [audioUrl, setAudioUrl] = useState(null);
    const [submitStatus, setSubmitStatus] = useState(null);
    const [audioBlob, setAudioBlob] = useState(null);

    const { voices, selectedVoiceIndex, setSelectedVoiceIndex, speak } = useTextToSpeech();
    const mediaRecorderRef = useRef(null);
    const audioChunks = useRef([]);

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
        try {
            const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
            const options = MediaRecorder.isTypeSupported("audio/wav")
                ? { mimeType: "audio/wav" }
                : {};

            mediaRecorderRef.current = new MediaRecorder(stream, options);

            mediaRecorderRef.current.ondataavailable = (e) => {
                if (e.data.size > 0) audioChunks.current.push(e.data);
            };

            mediaRecorderRef.current.onstop = () => {
                const blob = new Blob(audioChunks.current, { type: "audio/wav" });
                setAudioUrl(URL.createObjectURL(blob));
                setAudioBlob(blob);
                audioChunks.current = [];
            };

            audioChunks.current = [];
            mediaRecorderRef.current.start();
            setRecording(true);
        } catch (err) {
            console.error("Error accessing mic:", err);
        }
    };

    const pauseRecording = () => {
        if (mediaRecorderRef.current?.state === "recording") mediaRecorderRef.current.pause();
    };

    const resumeRecording = () => {
        if (mediaRecorderRef.current?.state === "paused") mediaRecorderRef.current.resume();
    };

    const stopRecording = () => {
        if (mediaRecorderRef.current) {
            mediaRecorderRef.current.stop();
            setRecording(false);
        }
    };

    const startSpeakingTest = () => {
        window.speechSynthesis.cancel(); // ❗ Hủy mọi utterance cũ trước khi đọc

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
        setCurrentQuestionIndex(nextIndex);
        speak(questions[nextIndex], () => resumeRecording());
    };

    const handleSubmit = async () => {
        try {
            setLoading(true);
            setSubmitStatus(null);
            if (!audioBlob) {
                setSubmitStatus({ type: "danger", message: "Không có dữ liệu ghi âm để gửi." });
                return;
            }

            const file = new File([audioBlob], "speaking.wav", { type: "audio/wav" });

            const formData = new FormData();
            formData.append("userId", localStorage.getItem("userId"));
            formData.append("lessonId", lessonId);
            formData.append("audio", file);

            const res = await Apis.post(endpoints["speaking-evaluate"], formData, {
                headers: { "Content-Type": "multipart/form-data" }
            });

            setSubmitStatus({ type: "success", message: "Gửi file và chấm điểm thành công." });
            console.log("Kết quả:", res.data);
        } catch (err) {
            console.error(err);
            setSubmitStatus({ type: "danger", message: "Lỗi khi gửi file hoặc chấm điểm." });
        }
        finally {
            setLoading(false);
        }
    };


    return (
        <Container className="mt-4">
            <h4>IELTS Speaking Task</h4>

            <Form.Group className="mb-3">
                <Form.Label>Giọng đọc:</Form.Label>
                <Form.Select
                    value={selectedVoiceIndex}
                    onChange={(e) => {
                        setSelectedVoiceIndex(parseInt(e.target.value));
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
                    <p><strong>Đã hoàn tất phần nói</strong></p>
                    {audioUrl && <audio controls src={audioUrl}></audio>}

                    <Button className="mt-2" onClick={handleSubmit} disabled={loading}>
                        {loading ? (
                            <>
                                <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                                Đang gửi và chấm điểm...
                            </>
                        ) : "Gửi và chấm điểm"} 
                    </Button>

                    {submitStatus && <Alert className="mt-3" variant={submitStatus.type}>{submitStatus.message}</Alert>}
                </div>
            )}
        </Container>
    );
};

export default SpeakingTask;
