import { useEffect, useState } from "react";
import { ListGroup, Card } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";

const parseJSON = (raw) => {
    try {
        return typeof raw === "string" ? JSON.parse(raw) : raw;
    } catch (err) {
        console.error("Lỗi parse JSON:", err);
        return {};
    }
};

const renderBySaveType = (section) => {
    const question = parseJSON(section.question);
    const answer = parseJSON(section.answer);
    const correctAnswer = parseJSON(section.correctAnswer);

    switch (section.saveType) {
        case "MULTIPLE_CHOICE":
            return Object.entries(question).map(([qKey, qText]) => (
                <Card key={qKey} className="mb-2">
                    <Card.Body>
                        <strong>{qText}</strong>
                        <ul className="mt-2">
                            {Object.entries(answer[qKey] || {}).map(([optKey, optText]) => (
                                <li key={optKey}>
                                    {optKey}. {optText}
                                    {correctAnswer[qKey]?.includes(optKey) && (
                                        <span className="text-success ms-2">(Đáp án đúng)</span>
                                    )}
                                </li>
                            ))}
                        </ul>
                    </Card.Body>
                </Card>
            ));

        case "MATCHING":
            return (
                <div className="mb-2">
                    <strong>Danh sách cặp cần ghép:</strong>
                    <ul className="mt-2">
                        {Object.entries(question).map(([qKey, qText]) => (
                            <li key={qKey}>
                                {qText} → {answer[correctAnswer[qKey]]}
                            </li>
                        ))}
                    </ul>
                </div>
            );

        case "COMPLETION":
        case "SHORT_ANSWER":
            return (
                <ul className="mb-2">
                    {Object.entries(question).map(([qKey, qText]) => (
                        <li key={qKey}>
                            {qText}
                            <br />
                            <small className="text-muted">Đáp án: {correctAnswer[qKey]?.join(", ")}</small>
                        </li>
                    ))}
                </ul>
            );

        case "TFNG_YNG":
            return (
                <ul className="mb-2">
                    {Object.entries(question).map(([qKey, qText]) => (
                        <li key={qKey}>
                            {qText}
                            <br />
                            <small className="text-muted">
                                Đáp án: {answer[correctAnswer[qKey]]}
                            </small>
                        </li>
                    ))}
                </ul>
            );

        default:
            return <em className="text-muted">Không xác định loại bài tập.</em>;
    }
};

const SectionList = ({ lessonId }) => {
    const [sections, setSections] = useState([]);

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

    return (
        <div>
            <h5>📘 Danh sách Section</h5>
            {sections.length === 0 && <p>📭 Chưa có section nào cho bài học này.</p>}

            <ListGroup className="mt-3">
                {sections.map((s) => (
                    <ListGroup.Item key={s.id} className="mb-3">
                        <h6 className="text-primary">{s.sectionTypeName}</h6>

                        {/* Render HTML content từ CKEditor */}
                        <div
                            className="ckeditor-content mb-3"
                            dangerouslySetInnerHTML={{ __html: s.content }}
                        ></div>

                        {/* Render theo saveType */}
                        {renderBySaveType(s)}
                    </ListGroup.Item>
                ))}
            </ListGroup>
        </div>
    );
};

export default SectionList;
