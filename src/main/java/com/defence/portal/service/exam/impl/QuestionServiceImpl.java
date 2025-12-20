package com.defence.portal.service.exam.impl;

import com.defence.portal.dto.exam.QuestionDto;
import com.defence.portal.entity.exam.Question;
import com.defence.portal.entity.exam.Section;
import com.defence.portal.exception.ResourceNotFoundException;
import com.defence.portal.repository.exam.QuestionRepository;
import com.defence.portal.repository.exam.SectionRepository;
import com.defence.portal.service.exam.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository,
                              SectionRepository sectionRepository,
                              ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.sectionRepository = sectionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Question createQuestion(QuestionDto questionDto) {
        Question question = modelMapper.map(questionDto, Question.class);

        // Set section if sectionId is provided
        if (questionDto.getSectionId() != null) {
            Section section = sectionRepository.findById(questionDto.getSectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + questionDto.getSectionId()));
            question.setSection(section);
        }

        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Long id, QuestionDto questionDto) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));

        modelMapper.map(questionDto, existingQuestion);

        // Update section if sectionId is provided
        if (questionDto.getSectionId() != null) {
            Section section = sectionRepository.findById(questionDto.getSectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + questionDto.getSectionId()));
            existingQuestion.setSection(section);
        }

        return questionRepository.save(existingQuestion);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionDto getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        return modelMapper.map(question, QuestionDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionsBySection(Long sectionId) {
        return questionRepository.findBySectionId(sectionId).stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
    }
}