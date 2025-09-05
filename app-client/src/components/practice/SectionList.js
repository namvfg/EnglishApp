// CHỈ THAY THẾ NỘI DUNG BÊN TRONG `SectionList` (React component) CỦA BẠN BẰNG BẢN NÀY

import { useEffect, useRef, useState } from "react";
import { ListGroup, Form, Button } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";

const parseJSON = (raw) => {
    try {
        return typeof raw === "string" ? JSON.parse(raw) : raw;
    } catch (err) {
        console.error("Lỗi parse JSON:", err);
        return {};
    }
};

const SectionList = ({ lessonId, durationRef }) => {
    const [loading, setLoading] = useState(false);
    const [sections, setSections] = useState([]);
    const [userAnswers, setUserAnswers] = useState({});
    const [startAt, setStartAt] = useState(new Date().toISOString());
    const questionCountRef = useRef(1);

    useEffect(() => {
        setStartAt(new Date().toISOString());
    }, []);

    useEffect(() => {
        const fetchSections = async () => {
            try {
                const res = await Apis.get(endpoints["sections"](lessonId));
                setSections(res.data || []);
            } catch (err) {
                console.error("Lỗi tải sections:", err);
            }
        };
        fetchSections();
    }, [lessonId]);

    const handleChange = (sectionId, questionKey, value) => {
        setUserAnswers((prev) => ({
            ...prev,
            [sectionId]: {
                ...prev[sectionId],
                [questionKey]: value,
            },
        }));
    };

    const handleSubmit = async () => {
        setLoading(true);
        const payload = {
            practiceSession: {
                lessonId,
                duration: durationRef.current,
                startAt
            },
            userAnswers: [],
            userId: localStorage.getItem("userId")
        };

        let totalScore = 0;
        let totalQuestions = 0;

        sections.forEach((section) => {
            const answers = userAnswers[section.id];
            const correctAnswers = parseJSON(section.correctAnswer);
            if (!answers || !correctAnswers) return;

            Object.entries(answers).forEach(([qKey, userAns]) => {
                const correct = correctAnswers[qKey];
                if (!correct) return;

                let isCorrect = false;

                switch (section.saveType) {
                    case "MULTIPLE_CHOICE": {
                        const userArray = Array.isArray(userAns) ? userAns : [userAns];
                        correct.forEach((rightAns) => {
                            const isCorrect = userArray.includes(rightAns);
                            payload.userAnswers.push({
                                sectionId: section.id,
                                answer: JSON.stringify({ [qKey]: rightAns }),
                                isCorrect,
                                score: isCorrect ? 1 : 0
                            });
                            totalScore += isCorrect ? 1 : 0;
                            totalQuestions++;
                        });
                        break;
                    }

                    case "COMPLETION":
                    case "SHORT_ANSWER": {
                        const correctArr = Array.isArray(correct) ? correct : [correct];
                        isCorrect = correctArr.includes(
                            typeof userAns === "string" ? userAns.trim().toLowerCase() : userAns
                        );
                        payload.userAnswers.push({
                            sectionId: section.id,
                            answer: JSON.stringify({ [qKey]: userAns }),
                            isCorrect,
                            score: isCorrect ? 1 : 0
                        });
                        totalScore += isCorrect ? 1 : 0;
                        totalQuestions++;
                        break;
                    }

                    default: {
                        isCorrect = JSON.stringify(correct) === JSON.stringify(userAns);
                        payload.userAnswers.push({
                            sectionId: section.id,
                            answer: JSON.stringify({ [qKey]: userAns }),
                            isCorrect,
                            score: isCorrect ? 1 : 0
                        });
                        totalScore += isCorrect ? 1 : 0;
                        totalQuestions++;
                        break;
                    }
                }
            });
        });

        try {
            const res = await Apis.post(endpoints["practice-evaluate"], {
                ...payload,
                score: totalScore
            });
            alert(`Gửi thành công! Điểm: ${res.data.score ?? totalScore}`);
        } catch (err) {
            console.error("Lỗi gửi bài làm:", err);
            alert("Gửi bài làm thất bại!");
        } finally {
            setLoading(false);
        }
    };

    questionCountRef.current = 1;
    return (
        <div>
            <h5 className="fw-bold text-success mb-3">Danh sách Section</h5>
            {sections.length === 0 ? <p>Chưa có section nào cho bài học này.</p> : null}

            <ListGroup className="mt-3">
                {sections.map((s) => (
                    <ListGroup.Item key={s.id} className="mb-4 border-0 shadow-sm rounded-3">
                        <h6 className="text-primary fw-bold mb-2">{s.sectionTypeName}</h6>
                        <div
                            className="ckeditor-content mb-3"
                            dangerouslySetInnerHTML={{ __html: s.content }}
                        ></div>
                        {renderFormByType(s)}
                    </ListGroup.Item>
                ))}
            </ListGroup>

            <div className="d-flex justify-content-center">
                <Button className="mt-4 px-4 py-2" variant="success" onClick={handleSubmit}>
                    {loading ? (
                        <>
                            <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                            Đang gửi và chấm điểm...
                        </>
                    ) : "Gửi và chấm điểm"}
                </Button>
            </div>
        </div>
    );

    function renderFormByType(section) {
        const question = parseJSON(section.question);
        const answer = parseJSON(section.answer);
        const correct = parseJSON(section.correctAnswer);

        switch (section.saveType) {
            case "MULTIPLE_CHOICE":
                return Object.entries(question).map(([qKey, qText]) => {
                    const options = Object.entries(answer[qKey] || {});
                    const correctChoices = correct?.[qKey] || [];
                    const isMultiple = correctChoices.length > 1;
                    const correctCount = correctChoices.length || 1;
                    const from = questionCountRef.current;
                    const to = from + correctCount - 1;
                    const labelText = from === to ? `Câu ${from}` : `Câu ${from}, ${to}`;
                    questionCountRef.current += correctCount;

                    return (
                        <Form.Group key={qKey} className="mb-3">
                            <Form.Label className="fw-semibold text-dark mb-1">
                                {labelText}: {qText}
                            </Form.Label>
                            {options.map(([optKey, optText]) => {
                                const name = `q-${section.id}-${qKey}`;
                                const checked = isMultiple
                                    ? (userAnswers[section.id]?.[qKey] || []).includes(optKey)
                                    : userAnswers[section.id]?.[qKey] === optKey;

                                const handleOptionChange = () => {
                                    if (isMultiple) {
                                        const prev = userAnswers[section.id]?.[qKey] || [];
                                        const newAnswers = prev.includes(optKey)
                                            ? prev.filter((k) => k !== optKey)
                                            : [...prev, optKey];
                                        handleChange(section.id, qKey, newAnswers);
                                    } else {
                                        handleChange(section.id, qKey, optKey);
                                    }
                                };

                                return (
                                    <Form.Check
                                        key={optKey}
                                        type={isMultiple ? "checkbox" : "radio"}
                                        name={name}
                                        label={`${optKey}. ${optText}`}
                                        value={optKey}
                                        checked={checked}
                                        onChange={handleOptionChange}
                                        className="ps-2 ms-2 mb-1"
                                    />
                                );
                            })}
                        </Form.Group>
                    );
                });

            case "MATCHING":
            case "TFNG_YNG":
                return Object.entries(question).map(([qKey, qText]) => (
                    <Form.Group key={qKey} className="mb-2">
                        <Form.Label className="fw-semibold text-dark mb-1">
                            Câu {questionCountRef.current++}: {qText}
                        </Form.Label>
                        <Form.Select
                            className="shadow-sm border-primary"
                            value={userAnswers[section.id]?.[qKey] || ""}
                            onChange={(e) => handleChange(section.id, qKey, e.target.value)}
                        >
                            <option value="">Chọn đáp án</option>
                            {Object.entries(answer).map(([aKey, aText]) => (
                                <option key={aKey} value={aKey}>{aText}</option>
                            ))}
                        </Form.Select>
                    </Form.Group>
                ));

            case "COMPLETION":
            case "SHORT_ANSWER":
                return Object.entries(question).map(([qKey, qText]) => (
                    <Form.Group key={qKey} className="mb-3">
                        <Form.Label className="fw-semibold text-dark mb-1">
                            Câu {questionCountRef.current++}: {qText}
                        </Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Nhập đáp án"
                            className="shadow-sm border-primary"
                            value={userAnswers[section.id]?.[qKey] || ""}
                            onChange={(e) => handleChange(section.id, qKey, e.target.value)}
                        />
                    </Form.Group>
                ));

            default:
                return <em className="text-muted">Loại bài không được hỗ trợ.</em>;
        }
    }
};

export default SectionList;
