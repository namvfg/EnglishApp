import { useEffect, useState } from "react";

import { ListGroup } from "react-bootstrap";
import Apis from "../../configs/Apis";


const SectionList = ({ lessonId }) => {
    const [sections, setSections] = useState([]);


    useEffect(() => {
        const fetchSections = async () => {
            try {
                const res = await Apis.get(`#`);
                setSections(res.data || []);
            } catch (err) {
                console.error("Lỗi tải sections:", err);
            }
        };


        fetchSections();
    }, [lessonId]);


    return (
        <div>
            <h5>Danh sách section</h5>
            <ListGroup>
                {sections.map((s) => (
                    <ListGroup.Item key={s.id}>{s.sectionTypeName}: {s.content}</ListGroup.Item>
                ))}
            </ListGroup>
        </div>
    );
};


export default SectionList;